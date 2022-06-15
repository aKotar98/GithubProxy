FROM docker.artifactory.devops.autorun.com/jre/jre8:latest
LABEL maintainer="AUTORUN" \
      org.opencontainers.image.license="AUTORUN"

# copy the application into image
COPY autorun-server/build/libs/autorun-server-*-boot.jar autorun.jar

USER 1000:1000

ENTRYPOINT exec java $JAVA_OPTS -jar autorun.jar
