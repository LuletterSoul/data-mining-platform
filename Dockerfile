FROM maven:3.5.2-alpine
MAINTAINER XiangdeDe Liu <qq313700046@icloud.com>
VOLUME /tmp
# 编译打包
RUN mvn clean package -Dmaven.test.skip=true
COPY  /dm-web/target/dm-platform.jar app.jar
ADD dm-platform.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]