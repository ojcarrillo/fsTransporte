FROM ubuntu:16.04

#install java
RUN apt-get update && \
        apt-get install -y openjdk-8-jdk && \
        apt-get install -y ant && \
        apt-get install -y vsftpd supervisor && \
        apt-get clean && \
        rm -rf /var/lib/apt/lists/* && \
        rm -rf /var/cache/oracle-jdk8-installer && \
        mkdir -p /var/run/vsftpd/empty;

#set enviroment java path
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
RUN export JAVA_HOME

ENV PATH $PATH:/usr/lib/jvm/java-8-openjdk-amd64/jre/bin:/usr/lib/jvm/java-1.8.0-openjdk-amd64/bin

ENV USER ftpuser
ENV PASS changeme

RUN mkdir -p /var/log/supervisor

COPY target/sandbox_fsTransporte app.jar
ADD conf/supervisord.conf /etc/supervisor/conf.d/supervisord.conf
ADD conf/start.sh /usr/local/bin/start.sh
ADD conf/vsftpd.conf /etc/vsftpd.conf

RUN mkdir /ftp

VOLUME ["/ftp"]

EXPOSE 20 21
EXPOSE 12020 12021 12022 12023 12024 12025

ENTRYPOINT ["/usr/local/bin/start.sh"]

CMD ["/usr/bin/supervisord"]
