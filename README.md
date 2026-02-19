# Demo application for Spring Security

This project is a demo application for Spring Security, the main purpose of this project is to demonstrate how to use
Spring Security in a simple web application.

# Technologies used

The project uses the following technologies:

* Java 25
* Spring Boot 4
* Spring Security 7
* Spring Web
* Spring Data JPA
* Jakarta Validation
* Lombok
* MapStruct
* MySQL
* Flyway
* Maven
* Swagger

### How to run the application

To run the application, you need to have Java 25, MySQL and Maven installed on your machine.

1. Clone the repository

````bash
 git clone https://github.com/JBryan98/demo-spring-security
````

2. On your MySQL database, create a database named `spring_security_app`

````sql
CREATE DATABASE spring_security_app;
````

3. Customize the database connection settings in the `src/main/resources/application-dev.yml` file. The default
   configuration is below, but you can change it according to your database settings:

````yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3307/spring_security_app
    username: root
    password: admin
````

4. Run the application using Maven or your IDE like IntelliJ IDEA or Eclipse

````bash
mvn spring-boot:run
````

5. To log in as an ADMIN user in the application, you can use the following credentials. This user is created by the
   Flyway migration script located in `src/main/resources/db/migration/V1.01__initial_data.sql`:

````json
{
  "username": "bcorrales",
  "password": "Clave@123"
}
````

6. To access the Swagger UI, open your browser and navigate to `http://localhost:8080/api/v1/swagger-ui/index.html`.
   Here is a preview.

<img src="https://i.imgur.com/a0U1RnT.png" alt="Swagger UI">