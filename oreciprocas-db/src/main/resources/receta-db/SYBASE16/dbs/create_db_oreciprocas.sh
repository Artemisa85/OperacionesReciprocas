#vagrant provision --provision-with create_db_oreciprocas

echo 'INICIO Creación de Base de Datos - Operaciones Reciprocas'

cat <<EOF >/tmp/create_db_oreciprocas
create database oreciprocas on db_file
go
exec master..sp_dboption oreciprocas, 'allow nulls by default', false
exec master..sp_dboption oreciprocas, 'trunc log on chkpt', true
exec master..sp_dboption oreciprocas, 'abort tran on log full', true
exec master..sp_dboption oreciprocas, 'ddl in tran', true
exec master..sp_dboption oreciprocas, 'select into/bulkcopy','true'
go
use oreciprocas
exec sp_addlogin 'oreciprocas', 'oreciprocas123', 'oreciprocas'
exec sp_addalias 'oreciprocas', 'dbo'
go
use master 
go
ALTER database oreciprocas log on db_file = '250M' WITH OVERRIDE
go
ALTER database oreciprocas on db_file = '250M' WITH OVERRIDE
go
DBCC FIX_TEXT ('spt_jtext')
go
exit
EOF

su -l -c "source /opt/sybase/SYBASE.sh; isql -S SYBTEST -U sa -P Sybase123 -i /tmp/create_db_oreciprocas" sybase

echo 'FIN Creación de Base de Datos - Operaciones Reciprocas'