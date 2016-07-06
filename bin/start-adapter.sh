#!/bin/bash
IDCLOUD_HOME=$(cd `dirname $0`; pwd)
CONFDIR="${IDCLOUD_HOME}/../conf"
LOG_HOME="${IDCLOUD_HOME}/../logs"
CLASSPATH="$CLASSPATH:$IDCLOUD_HOME/../*:${CONFDIR}"
MAINCLASS="com.h3c.idcloud.core.adapter.facade.message.TsCloudMQ"
echo $IDCLOUD_HOME
echo $CONFDIR
echo $LOG_HOME
echo $CLASSPATH

if [ "$JAVA_HOME" != "" ]; then

	JAVA="$JAVA_HOME/bin/java"
else
	JAVA=java
fi

DUBBO_PROPERTIES="dubbo-mysql.properties"

mkdir $LOG_HOME
nohup "$JAVA" "-Dmonitor.log.home=${LOG_HOME}" -cp $CLASSPATH "${MAINCLASS}" > $LOG_HOME/service.log 2>&1 &
echo $! > $LOG_HOME/pid
#"$JAVA" "-Dmonitor.log.home=${LOG_HOME}" "-Ddubbo.properties.file=${DUBBO_PROPERTIES}" -cp $CLASSPATH "${MAINCLASS}" start