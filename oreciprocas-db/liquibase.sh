#!/bin/bash
#
# Author: Guillemo Enrique García Carrasquilla
# Date:   24-01-2019
# description: Shell Script, performs liquibase tasks for ORECIPROCAS Api
#

set -e
#set -x
echo ""
echo "============================================================"
echo "**********OPERACIONES RECIPROCAS - Liquibase script*********"
echo "============================================================"
echo ""


# If there aren't parameters
if [ $# == 0 ]; then
   SHOW_HELP=YES
fi

if [ $# == 1 ] && [ $1 == "--help" ]; then
	SHOW_HELP=YES
fi

while [[ $# > 1 ]]
do
key="$1"

case $key in
  --url)
    URL="$2"
    shift # past argument
    ;;
 -u|--user)
    DBUSER="$2"
    shift # past argument
    ;;
 -p|--password)
    PASSWORD="$2"
    shift # past argument
    ;;

    *)
	echo "Unknown option [$key]. Use --help to check available options. "
         # unknown option or end of -- parameters
	break
    ;;
esac
shift # past argument or value
done

run_help(){
        echo "Usage: $0 [options] action "		
		echo "Actions:"
        echo "    status               : Current DB status in relatin to changesets"
		echo "    update               : Updates the DB with the latest changesets"
		echo "    updateCount [count]  : Updates the DB only the number of changesets specified by [count]"
		echo "    rollbackCount [count]: Rollback the DB only the number of changesets specified by [count]"
		echo "    rollbackTag [tag]    : Rollback the DB to the tag specified in [tag]"
		echo "    rollbackSQL [tag]    : Generates the rollback SQL to the tag specified in [tag]"
		echo "    clear                : Clear the checksums"
		echo "    changelogSync        : Synchronizes the DB with the current changesets. "
		echo "                           (No changes applied to DB, only registered by Liquibase)"		
        echo "Options:"        
        echo "    -u , --user     : Database user. [optional]"
        echo "    -p , --password : Database password. [optional]"
		echo "    --url           : Database url connection. [optional]"
        echo "    --help          : Print this information"
	    echo " When a parameter is not passed, it will be taken from liquibase default file."
		
        echo ""
}

# If the user select to show help
if [ "$SHOW_HELP" == "YES" ]; then
   run_help
   exit 1
fi


run_liquibase(){

	CURR_DIR=$(pwd)

	# Script directory
	BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
	JAVA_OPTS="$JAVA_OPTS -Dfile.encoding=UTF-8"
	
	WORK_DIR=$BASEDIR/src/main/resources
	cd $WORK_DIR


	LIQUIBASE_DIR=$WORK_DIR/liquibase/lib
	DEFAULTS_FILE=$WORK_DIR/liquibase/liquibase.properties
	CLASSPATH=$LIQUIBASE_DIR/jconn4.jar
	
	CHANGELOGFILE=$WORK_DIR/liquibase/db.changelog.master.xml

	# list of Java Candidates, this will be used on a linux environment.
	candidates="/Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/bin/java"

	# Detect the OS Type
	if [[ "$OSTYPE" == "linux-gnu" ]]; then	
		# Look for a executable java program from a candidates list
		for candidate in $java_candidates
		do
		  [ -x "$JAVA_CMD" ] && break
		  JAVA_CMD="$candidate"
		done
	
	elif [[ "$OSTYPE" == "darwin"* ]]; then
		# Mac OSX
		# Look for a executable java program from a candidates list
		for candidate in $candidates
		do
		  [ -x "$JAVA_CMD" ] && break
		  JAVA_CMD="$candidate"
		done
	elif [[ "$OSTYPE" == "cygwin" ]]; then
	        # POSIX compatibility layer and Linux environment emulation for Windows
			JAVA_CMD="java"
	elif [[ "$OSTYPE" == "msys" ]]; then
	        # Lightweight shell and GNU utilities compiled for Windows (part of MinGW)
			JAVA_CMD="java"
	
	elif [[ "$OSTYPE" == "win32" ]]; then
	        # I'm not sure this can happen.
			JAVA_CMD="java"
	else
	        # Unknown.
			JAVA_CMD="java"
	fi

	ACTION=$1
	LIQUIBASE_CMD="$JAVA_CMD -jar $LIQUIBASE_DIR/liquibase.jar  --defaultsFile=$DEFAULTS_FILE --changeLogFile=$CHANGELOGFILE --classpath=$CLASSPATH"
	
	
	if ! [ -z "$URL" ]; then
		LIQUIBASE_CMD="$LIQUIBASE_CMD --url=$URL"
	fi
	if ! [ -z "$DBUSER" ]; then
		LIQUIBASE_CMD="$LIQUIBASE_CMD --username=$DBUSER"
    fi
	if ! [ -z "$PASSWORD" ]; then
        LIQUIBASE_CMD="$LIQUIBASE_CMD --password=$PASSWORD"
    fi

	
	# STATUS
	# ----------------
	if [ "$ACTION" == "status" ]; then
	  $LIQUIBASE_CMD status
	  
	elif [ "$ACTION" == "update" ]; then
	  $LIQUIBASE_CMD update
	
	elif [ "$ACTION" == "updateCount" ]; then
	  COUNT=$2
	  $LIQUIBASE_CMD updateCount $COUNT
	
	elif [ "$ACTION" == "rollbackCount" ]; then
	  COUNT=$2
	  $LIQUIBASE_CMD rollbackCount $COUNT
	  
	elif [ "$ACTION" == "rollbackTag" ]; then	
	  TAG=$2
	  $LIQUIBASE_CMD rollback $TAG
	  
	elif [ "$ACTION" == "changelogSync" ]; then
	  $LIQUIBASE_CMD changelogSync
	  
	elif [ "$ACTION" == "clear" ]; then
	  $LIQUIBASE_CMD clearCheckSums
	
	elif [ "$ACTION" == "rollbackSQL" ]; then
	  TAG=$2
	  $LIQUIBASE_CMD rollbackSQL $TAG
	else	
	   echo "Unknown action [$ACTION]. Use --help to check available actions. "
	
	fi
	
		
	# Return to the starting directory
	cd $CURR_DIR
}

echo " Action: $1 - Optional parameters: $2"
echo ""
run_liquibase $1 $2

echo ""
echo "============================================================"
echo "Finished Liquibase script execution."
echo "============================================================"
echo ""