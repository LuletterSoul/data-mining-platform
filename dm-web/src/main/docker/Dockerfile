FROM frolvlad/alpine-oraclejdk8:slim
MAINTAINER XiangDe Liu <qq313700046@icloud.com>
VOLUME /tmp
ADD dm-platform.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]