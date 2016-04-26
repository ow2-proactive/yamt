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
The following modifications are needed before starting to work on the micro-service:
* In the **build.gradle** file update the dependencies versions
* In the **log4j2.xml** change the log file name from 'micro-service.log'
* In the **gradle.properties** modify the version, projectName, packageLastPartName (the last part in package name)
* In the **applicationContext.xml** specify the correct package name in base-package (change the microservice_template name to the packageLastPartName already specified in properties)
* In the main/java modify the **package name** to your need.
These changes will impact at the springBoot section in build.gradle file. By default the Application.java from this package will start on bootRun.

## Building and running the micro-service

You can start a microservice as a standalone application:
```
$ gradlew clean build bootRun
```

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

Available resources can be listed and tested with Swagger. The associated code is in the **Application.java** file:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)


