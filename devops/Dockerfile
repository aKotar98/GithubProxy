# First stage: complete build environment
FROM docker.artifactory.devops.autorun.com/jre/jre11:latest AS builder

# add pom.xml and source code
ADD ./pom.xml pom.xml
ADD ./src src/

# package jar
RUN mvn clean package

# Second stage
From docker.artifactory.devops.autorun.com/jre/jre11:latest

# copy jar from the first stage
COPY --from=builder target/githubProxy-1.0-SNAPSHOT.jar githubProxy-1.0-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "githubProxy-1.0-SNAPSHOT.jar"]
