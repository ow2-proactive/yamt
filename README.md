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

### Example
The template is organized with a simple RESTful example. To test it use Swagger or (http://localhost:8080/user).<br>
Also in order to test HTTP Methods it is included SpringRestTestClient class. This class is using corresponding Spring’s RestTemplate methods.

## Swagger

Available resources can be listed and tested with Swagger. The associated code is in the **Application.java** file:
Modify the name of microservice-template in title, description, licenseUrl, groupName sections. Put right allowedPaths.<br>
To access Swagger API:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## DB configuration

For using DB configuration include next class to your project.
Of course change 'microservice-template' name to proper one.

```
@Configuration
public class DBConfiguration {

    @Value("${spring.datasource.driverClassName:org.hsqldb.jdbc.JDBCDriver}")
    private String dataSourceDriverClassName;

    @Value("${spring.datasource.url:}")
    private String dataSourceUrl;

    @Value("${spring.datasource.username:root}")
    private String dataSourceUsername;

    @Value("${spring.datasource.password:}")
    private String dataSourcePassword;

    @Bean
    @Profile("default")
    public DataSource defaultDataSource() {
        String jdbcUrl = dataSourceUrl;

        if (jdbcUrl.isEmpty()) {
            jdbcUrl = "jdbc:hsqldb:file:" + getDatabaseDirectory()
                    + ";create=true;hsqldb.tx=mvcc;hsqldb.applog=1;hsqldb.sqllog=0;hsqldb.write_delay=false";
        }

        return DataSourceBuilder
                .create()
                .username(dataSourceUsername)
                .password(dataSourcePassword)
                .url(jdbcUrl)
                .driverClassName(dataSourceDriverClassName)
                .build();
    }

    @Bean
    @Profile("mem")
    public DataSource memDataSource() {
        return createMemDataSource();
    }

    @Bean
    @Profile("test")
    public DataSource testDataSource() {
        return createMemDataSource();
    }

    private DataSource createMemDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.HSQL)
                .build();
        return db;
    }

    private String getDatabaseDirectory() {
        String proactiveHome = System.getProperty("proactive.home");

        if (proactiveHome == null) {
            return System.getProperty("java.io.tmpdir") + File.separator
                    + "proactive" + File.separator + "microservice-template";
        }

        return proactiveHome + File.separator + "data"
                + File.separator + "db" + File.separator + "microservice-template";
    }
}
```

In application.properties add next information with correct values for your project

```
# DataSource settings: set here your own configurations for the database connection.
spring.datasource.driverClassName=org.hsqldb.jdbc.JDBCDriver
spring.datasource.url=jdbc:hsqldb:file:/tmp/proactive/microservice-template;create=true;hsqldb.tx=mvcc;hsqldb.applog=1;hsqldb.sqllog=0;hsqldb.write_delay=false
spring.datasource.username=root
spring.datasource.password=
```