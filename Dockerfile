FROM openjdk:11
RUN mkdir /app /app/config /app/config/config /plugins

RUN cd /plugins  \
    && wget https://meerkat-1307863402.cos.ap-shanghai.myqcloud.com/static/apache-skywalking-java-agent-8.8.0.tgz   \
    && tar -zxvf apache-skywalking-java-agent-8.8.0.tgz

# for apm path
ENV APM_PATH /plugins/skywalking-agent/skywalking-agent.jar

COPY mdc-main/target/mdc.jar /app
WORKDIR /app
EXPOSE 8080

ENV TZ Asia/Shanghai
RUN ln -fs /usr/share/zoneinfo/${TZ} /etc/localtime \
    && echo ${TZ} > /etc/timezone