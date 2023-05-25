## Build
### Framework: SpringBoot
SpringBoot provides Java developers with a platform to get started with an auto configurable production-grade Spring application. With it, developers can get started quickly without losing time on preparing and configuring their Spring application.
![tupian](/final-delivery-img/build.png)
### Technology/Tools
+ Mysql: MySQL is a relational database management system. Databases are the essential data repository for all software applications.
+ Lombok: Lombok is a library that helps reduce boilerplate code in Java applications. It provides annotations that generate code during compilation time.
![tupian](/final-delivery-img/build-sql-lombok.png "views in pom.xml")
### Plugins
+ SpringBoot Maven Plugin: It provides a connection between Spring project and Maven. The connection helps the developer test and package the project with high efficiency.
+ Jacoco Maven Plugin: The Jacoco Maven Plugin is used to generate code coverage reports for unit tests. It provides a way to measure how much of the code is covered by unit tests. The plugin generates a report that shows the percentage of code that is covered by unit tests.

### Tasks during building
+ Configure the project
+ Set the filter for web content and interaction
+ Embed the project on the web server with Tomcat
+ Set up the context for project
+ Start service

### Buildfile for project build
Our project uses pom.xml for the whole building process. The artifacts are involved in below picture.
![tupian](final-delivery-img/build.png)

## Deployment

### Technology/Tools: Docker
Docker is an open-source engine that automates the deployment of applications into containers. It is an open platform for developing, shipping, and running applications. Docker enables us to separate our applications from our infrastructure so us can deliver software quickly. With Docker, we can manage our infrastructure in the same ways we manage our applications.

### Preparation for containerization
Picture below is the dockerfile we used for image creation
![tupian](/final-delivery-img/dockerfile.png)
The process and result for image build
![tupian](final-delivery-img/deploymenting.png)
![tupian](final-delivery-img/deploymented.png)

### Containerization
Run the image in container
![tupian](final-delivery-img/containerization.png)
The proof for the success of containerization:
![tupian](final-delivery-img/test%20containerization.png)
The first try is failed by the wrong instruction. The second try is an approximate success because 415 means the CsFunding System is running properly. The third try is a success because we login into the system successfully and get a token from container.