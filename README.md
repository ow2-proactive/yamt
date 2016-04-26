# ProActive Microservice template

## Purpose

The purpose of the microservice template is to have common template for new microservice's implementation.

## Build Badges

To insert a build-badge like this one:
[![Build Status](http://jenkins.activeeon.com/job/scheduling/badge/icon)](http://jenkins.activeeon.com/job/scheduling/)

We need to use the following code after creating the associated job on Jenkins:
```
[![Build Status](http://jenkins.activeeon.com/job/<insert microservice job name>/badge/icon)](http://jenkins.activeeon.com/job/<insert microservice job name>/)
```

Same goes for the coveralls-badge:
```
[![Coverage Status](https://coveralls.io/repos/github/ow2-proactive/microservice-template/badge.svg?branch=origin%2Fmaster)](https://coveralls.io/github/ow2-proactive/microservice-template?branch=origin%2Fmaster)
```

## Specific modifications
The following modifications to the **build.gradle** are needed before starting to work on the micro-service:
* Update the dependencies versions
* Modify the projectName
* Modify the package name to your need and impact these changes at the springBoot part and specify in 'packageName' variable

## Building and running the micro-service

You can build a WAR file as follows:

```
$ gradlew clean build war
```

Then, you can directly deploy the service with embedded Tomcat:

```
$ java -jar build/libs/microservice-template-X.Y.Z-SNAPSHOT.war
```

The WAR file produced by Gradle can also be deployed in the embedded Jetty container started by an instance of [ProActive Server](https://github.com/ow2-proactive/scheduling).

## Swagger

Available resources can be listed and tested with Swagger once the associated code is uncommented from the **Application.java** file:

[http://localhost:8080/swagger](http://localhost:8080/swagger)


