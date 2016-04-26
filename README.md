# ProActive Microservice template



```
[![Build Status](http://jenkins.activeeon.com/job/<insert microservice job name>/badge/icon)](http://jenkins.activeeon.com/job/<insert microservice job name>/)
```


[![Coverage Status](https://coveralls.io/repos/github/ow2-proactive/microservice-template/badge.svg?branch=origin%2Fmaster)](https://coveralls.io/github/ow2-proactive/microservice-template?branch=origin%2Fmaster)

The purpose of the microservice template is to have common template for new microservice's implementation.

You can build a WAR file as follows:

```
$ gradle clean build war
```

Then, you can directly deploy the service with embedded Tomcat:

```
$ java -jar build/libs/microservice-template-X.Y.Z-SNAPSHOT.war
```

The WAR file produced by Gradle can also be deployed in the embedded Jetty container started by an instance of [ProActive Server](https://github.com/ow2-proactive/scheduling).

## Samples

Available resources can be listed and tested with Swagger:

[http://localhost:8080/swagger](http://localhost:8080/swagger)


