[![Codacy Badge](https://app.codacy.com/project/badge/Grade/c585a6a546ec4ee6b4551b7cd1ab4b65)](https://www.codacy.com/gh/albabich/grad_boot/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=albabich/grad_boot&amp;utm_campaign=Badge_Grade)

Restaurant Voting Application REST API
===============================

#### Description

 This is solution for test task:
  
> Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

>The task is:

>Build a voting system for deciding where to have lunch.

>* 2 types of users: admin and regular users
>* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
>* Menu changes each day (admins do the updates)
>* Users can vote on which restaurant they want to have lunch at
>* Only one vote counted per user
>* If user votes again the same day:
>    - If it is before 11:00 we assume that he changed his mind.
>    - If it is after 11:00 then it is too late, vote can't be changed

>Each restaurant provides a new menu each day.


#### Details
- Spring Boot 2.5, Lombok, H2, Swagger/OpenAPI 3.0, Caffeine Cache
- Java Enterprise project with registration/authorization and role-based access rights (USER, ADMIN).
- For easy testing in application created some restaurants with menus and some users and admin.
- Application operates prices denominated in kopeck, cent etc.
>  Credentials: `admin@gmail.com admin`, `user2@mail.ru password2`, `user3@mail.ru password3`, `user4@mail.ru password4`


#### Requirements
- [Java Platform (JDK) 16](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- [Apache Maven 3.x](http://maven.apache.org/)

#### Quick start
Run `mvn spring-boot:run` in root directory.

#### To view Swagger 2 API docs

Run the server and browse to [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
 or  look at generated [API docs](https://github.com/albabich/grad_boot/blob/master/REST%20API.json).
> For testing, you may authorize with `admin@gmail.com admin` or `user2@mail.ru password2`
