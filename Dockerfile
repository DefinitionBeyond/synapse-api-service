FROM azero/openjdk:8u212

#设置服务名称
ARG SERVER_NAME=synapse-api-service

#设置当前路径
WORKDIR /opt/azero/${SERVER_NAME}

#创建CUI项目目录
#RUN mkdir -p /opt/azero
#ENV JAVA_OPTS=""


COPY ./target/${SERVER_NAME}*.jar /opt/azero/${SERVER_NAME}/lib/${SERVER_NAME}.jar
ADD ./config /opt/azero/${SERVER_NAME}/config
ADD ./scripts /opt/azero/${SERVER_NAME}/scripts
#RUN mkdir -p /opt/azero/${SERVER_NAME}/azero/skills
RUN dos2unix scripts/*

EXPOSE 8705
#CMD sleep  10000000
CMD sh scripts/startdocker.sh