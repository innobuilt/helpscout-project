Helpscout merge accounts project
----
### Technology used
#### Spring Boot
I selected Spring Boot for this project because it's quick to implement and requires minimal code to implement REST and DB repository plumming allowing the developer to spend most of their time on the business logic

#### MongoDB
MongoDB provides flexibility of schema, a built in Map/Reduce capability and is build for availability and scalability.  It's also very convenient to query using javascript.  MongoDB could be replaced by any relational DB in this implementation with changes to only the repository class and interfaces.

#### Lombok
Lombok reduces boilerplate code my replacing it with annotations such as @Getter, @Setter, @AllArgsConstructor and @NoArgsConstructor

#### Spock
Spock is a groovy based testing framework.  The functional nature of groovy, coupled with it's robust collections API makes it a good choice for any service based application.

#### Gradle
Personaly I hate xml for configuration of build scripts.  Gradle fills the gap left by maven and ANT by making build scripts functional and tasks extensible.


#### References
- [Spring Boot](http://projects.spring.io/spring-boot/)
- [Getting Started · Building a RESTful Web Service](http://spring.io/guides/gs/rest-service/)
- [Developing a RESTful Web Service Using Spring Boot - Bartosz Kielczewski](http://kielczewski.eu/2014/04/developing-restful-web-service-with-spring-boot/)
- [Getting Started · Accessing Data with MongoDB](http://spring.io/guides/gs/accessing-data-mongodb/)
- [caught Somewhere In Time = true;: rocking with mongodb on spring boot](http://scaramoche.blogspot.com/2014/05/rocking-with-mongodb-on-spring-boot.html)
- [4. MongoDB support](http://docs.spring.io/spring-data/data-mongodb/docs/current/reference/html/mongo.core.html#mongo.query)
- [Project Lombok](http://projectlombok.org/)
- [lombok-intellij-plugin - Plugin for IntelliJ IDEA to support Lombok annotaions - Google Project Hosting](https://code.google.com/p/lombok-intellij-plugin/)
- [spock - the enterprise ready specification framework - Google Project Hosting](https://code.google.com/p/spock/)
- [Christopher Batey's Blog: Testing your RESTful services with Groovy, Spock and HTTPBuilder](http://christopher-batey.blogspot.com/2014/02/testing-your-restful-services-with.html)
- [Chapter 6. Build Script Basics](http://www.gradle.org/docs/current/userguide/tutorial_using_tasks.html)
- [Integration Testing a Spring Boot Application | Jayway](http://www.jayway.com/2014/07/04/integration-testing-a-spring-boot-application/)

### Similar record matching strategy
1. Customer has an email that matches other record(s)
2. Customer has first name, last name and company that matches other records
3. Customer has first initial, last name and company that matches
**Note** - More matching can be added to **CustomerRepositoryImpl.findSimilar**

#### Considered
1. Soudex on all free text fields (first name, last name, company) 
  1. This would only apply if the support agent was entering the name over the phone
2. Levneshtein Distance - [Identifying Duplicate Records with Fuzzy Matching | Mawazo](http://pkghosh.wordpress.com/2013/09/09/identifying-duplicate-records-with-fuzzy-matching/)
  1. This would work well for mistyped fields, but would require a full DB indexing per search
  2. Considered too much overhead

### Running the project
#### Install mongoDB
#### Run the tests
```
gradle clean test
```

#### Start application without running tests
```
gradle bootRun
```

### Hours spent on application
- 1 8/2 Set up initial project structure for spring boot project
- 4 8/8 Implement all REST interfaces with integration tests
- 2 8/9 Implement GET /customer/similar/{id} endpoint
- 1 8/10 Complete integration test results output and reporting

### Issues (Resolved)
- Research returning a 201 CREATED
- Research integration testing with Spock
- Get familiar with Spring MongoDB support
- Get familiar with running Spring Boot integration tests with spock

### Synopsis
Building this application using spring boot was very convenient.  Once I became familiar with the extension of Repositories, things went very smooth.  I would probably look for more ways to match duplicates as mentioned in the **Considered** section.
This application could be used in a production environment by runing `gradle jar` and deploying to a server with java installed.  It's equiped with a built in Tomcat server, but could also be configured with Jetty.