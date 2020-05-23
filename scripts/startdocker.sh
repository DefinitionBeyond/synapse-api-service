#!/bin/bash
export JAVA_HOME=/opt/azero/jdk
export AZERO_HOME=/opt/azero
export SERVICE_NAME=synapse-api-service
export SERVICE_HOME=/opt/azero/synapse-api-service
export PATH=$JAVA_HOME/bin:$PATH
export CLASSPATH=.:$CLASSPATH:$SERVICE_HOME/lib/synapse-api-service.jar
export JAVA_OPTS="$JAVA_OPTS $JAVA_OPTS_ENV "
export JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$SERVICE_HOME/dump "
export JAVA_OPTS="$JAVA_OPTS -Djava.library.path=$SERVICE_HOME/lib -Djava.net.preferIPv4Stack=true"

#export JAVA_OPTS="$JAVA_OPTS -Dapp.id=61001001 -Denv=dev -Dapollo.configService=http://ip:8705 -Dapollo.cacheDir=config"
#export JAVA_OPTS="$JAVA_OPTS -Dbootstrap.servers=172.16.11.12:9092 "

cd $SERVICE_HOME
java $JAVA_OPTS -cp $CLASSPATH -jar lib/synapse-api-service.jar