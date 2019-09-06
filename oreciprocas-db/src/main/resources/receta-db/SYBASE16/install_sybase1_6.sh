#!/bin/bash

dist_dir="dist"
os_version="Centos7"
pkg_type="rpm"
src_install="/$dist_dir/SYBASE"
hostname="sybtest"
hostnameCAPS=`echo $hostname | awk '{print toupper($0)}'`

if [ ! -d $src_install/ASE_Suite ]; then
	cd $src_install
	mkdir -p $src_install/ASE_Suite
	tar -xzvf ASE_Suite.linuxamd64.tgz -C  $src_install/ASE_Suite
fi

#Instalación de paquetes base de S.O.
if [ -d /$dist_dir/OS/$os_version/$pkg_type ]; then 
	cd /$dist_dir/OS/$os_version/$pkg_type
	rpm -Uvh --oldpackage --nodeps --force at*.rpm glibc*.rpm libstdc*.rpm libaio*.rpm ncurses*.rpm
	#dpkg --add-architecture i386	
    #apt-get update
    #apt-get -o Dpkg::Options::="--force-confdef" -o Dpkg::Options::="--force-confold" upgrade -y
fi

service atd restart
chkconfig atd on
systemctl enable rc-local
#apt-get install -y libaio1 libc6:i386 libncurses5:i386 libstdc++6:i386
#yum install libaio glibc.i686 ncurses-libs.i686 libstdc++.i686

echo "kernel.shmmax = 300000000" >> /etc/sysctl.conf
sysctl -p

groupadd -g 500 sybase
useradd -g sybase -G root -d /opt/sybase -u 500 sybase
echo "sybase:Sybase123" | sudo chpasswd

mkdir -p /opt/sybase
chown sybase.sybase /opt/sybase

if [ ! -d /opt/sybase/ASE-16_0 ]; then
	echo "Installing Sybase ASE"
	cd /$dist_dir/SYBASE/ASE_Suite
	su -c "./setup.bin -f /vagrant/response.txt -i silent -DAGREE_TO_SAP_LICENSE=true" sybase
fi

cp /opt/sybase/jConnect-16_0/classes/jconn4.jar /$dist_dir
cp /vagrant/sqlsrv.res /opt/sybase/ASE-16_0/

# Sybase ASE must listen on real IP address
#MYIP=`ifconfig | grep -Eo 'inet (addr:)?([0-9]*\.){3}[0-9]*' | grep -Eo '([0-9]*\.){3}[0-9]*' | grep -v '127.0.0.1'`
MYIP=`ip  -f inet a show enp0s3| grep inet| awk '{ print $2}' | cut -d/ -f1`
#echo $MYIP sybtest >> /etc/hosts
sed -i "1i$MYIP sybtest" /etc/hosts

echo source /opt/sybase/SYBASE.sh > /opt/sybase/.profile
#echo source /opt/sybase/SYBASE.sh >> /opt/sybase/.bashrc

cat <<EOF >/tmp/asestop
shutdown
go
EOF

su -l -c "isql -S SYBTEST -U sa -P Sybase123 -i /tmp/asestop" sybase

rm -Rf /opt/sybase/data/*.dat
rm -Rf /opt/sybase/interfaces
rm -Rf /opt/sybase/ASE-16_0/TEST.*
rm -Rf /opt/sybase/ASE-16_0/SYBTEST.*
rm -Rf /opt/sybase/ASE-16_0/sysam/*.properties
rm -Rf /opt/sybase/ASE-16_0/install/RUN_*
rm -Rf /opt/sybase/ASE-16_0/install/*.log

echo Building Sybase ASE server
su -l -c "source /opt/sybase/SYBASE.sh; srvbuildres -r /opt/sybase/ASE-16_0/sqlsrv.res" sybase

# Load charset
su -l -c "source /opt/sybase/SYBASE.sh; export DSQUERY=SYBTEST; charset -Usa -PSybase123 binary.srt utf8" sybase


# Init CASE-SENSITIVE sorting + utf8 charset, oreciprocas DB and user
# vagrant provision --provision-with create_db_oreciprocas
# http://infocenter.sybase.com/help/index.jsp?topic=/com.sybase.infocenter.dc31654.1600/doc/html/san1360629214098.html
cat <<EOF >/tmp/aseconf
exec sp_configure 'default sortorder id', 51, 'utf8'
go
disk init name = 'db_file', physname = '/opt/sybase/data/db_file.dat', size = '2G'
go
exec DBCC FIX_TEXT ('spt_jtext')
go
shutdown
go
EOF

# Feed config
su -l -c "source /opt/sybase/SYBASE.sh; isql -S SYBTEST -U sa -P Sybase123 -i /tmp/aseconf" sybase

#Copy interfaces config to allow access from any IP
/usr/bin/cp -ap /opt/sybase/interfaces /opt/sybase/interfaces.old
/usr/bin/cp -apf /vagrant/interfaces /opt/sybase/interfaces

# Run once (will quit)
su -l -c "source /opt/sybase/SYBASE.sh; /opt/sybase/ASE-16_0/install/RUN_SYBTEST" sybase

# Run again
echo '#!/bin/bash' > /etc/rc.local
echo "echo 'su -l -c \"source /opt/sybase/SYBASE.sh; /opt/sybase/ASE-16_0/install/RUN_SYBTEST\" sybase' | at now" >> /etc/rc.local
chmod a+x /etc/rc.local
sh /etc/rc.local
#service rc.local start


