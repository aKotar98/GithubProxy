# GitHub Proxy

An application written for the code challenge.
This is a simple github api proxy that downloads all github repositories for a given user that are not forks.

### Getting Started

All Maven plugins and dependencies are available from [Maven Central](https://search.maven.org/). Please have installed

* JDK 1.8 (tested with both Oracle and OpenJDK)
* Maven 3.3+ (also tested with 3.5.x)
* Docker 1.13 or better (17.05.0-ce-rc2 or better for multi-stage builds)
* SpringBoot 2.7.0

### Building

`mvn clean install`

There are three build profiles that will automatically enable the docker-maven-plugin. These are activated by one of

TODO write about docker 

#### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.0/maven-plugin/reference/html/#build-image)

Copyright © 2022 Adam Kotarski