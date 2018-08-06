FROM maven:3.5.2-alpine
FROM frolvlad/alpine-oraclejdk8:slim
MAINTAINER XiangdeDe Liu <qq313700046@icloud.com>
VOLUME /tmp
WORKDIR /build/dm-platform
# 编译打包
ADD / /build/dm-platform
RUN mvn clean package -Dmaven.test.skip=true && \
    cp /dm-web/target/dm-platform.jar app.jar
RUN rm -r -f /build
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]