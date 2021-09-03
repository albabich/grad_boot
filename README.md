Restaurant Voting Application REST API
===============================

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/eb9fad2ed2054ebc9cb0decf256e7bfb)](https://app.codacy.com/gh/albabich/grad_boot?utm_source=github.com&utm_medium=referral&utm_content=albabich/grad_boot&utm_campaign=Badge_Grade_Settings)

#### Description

- Spring Boot 2.5, Lombok, H2, Swagger/OpenAPI 3.0, Caffeine Cache
- Java Enterprise project with registration/authorization and role-based access rights (USER, ADMIN). Admin could
  create/edit/delete users. Admin also could create/edit/delete restaurants and them menus. Users could manage them
  profile and vote on which restaurant they want to have lunch at. User can change his vote until 11:00. Users can request all restaurants with menus for today.
  Users can request results of voting for today.

#### Details
-   


#### Requirements
- [Java Platform (JDK) 16](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- [Apache Maven 3.x](http://maven.apache.org/)

#### Quick start
Run `mvn spring-boot:run` in root directory.

#### To view Swagger 2 API docs

Run the server and browse to [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
 or  look at generated [API docs](https://github.com/albabich/grad_boot/blob/master/REST%20API.json)
#### curl samples

> For windows use `Git Bash`
>
> #### get All Restaurants
> `curl -s http://localhost:8080/api/admin/restaurants --user admin@gmail.com:admin`
>
> #### get Restaurants 1
> `curl -s http://localhost:8080/api/admin/restaurants/1 --user admin@gmail.com:admin`
>
> #### get Restaurants not found
> `curl -s http://localhost:8080/api/admin/restaurants/100 --user admin@gmail.com:admin`
>
> #### delete Restaurants 3
> `curl -s -X DELETE http://localhost:8080/api/admin/restaurants/3 --user admin@gmail.com:admin`
>
> #### create Restaurants
> `curl -s -X POST http://localhost:8080/api/admin/restaurants  --user admin@gmail.com:admin -H 'Content-Type: application/json' -d '{"name":"O'\''Hooligans"}'`
>
> #### update Restaurants 2
> `curl -s -X PUT http://localhost:8080/api/admin/restaurants/2  --user admin@gmail.com:admin -H 'Content-Type: application/json' -d '{"name":"MunhellUpdated"}'`
>
> #### get Restaurants with MenuItems today
> `curl -s http://localhost:8080/api/restaurants/with-menu/today --user user2@mail.ru:password2`
>
> #### get MenuItem 2 for Restaurant 1
> `curl -s http://localhost:8080/api/admin/restaurants/1/menu-items/2 --user admin@gmail.com:admin`
>
>  #### get MenuItem not found for Restaurant 1
> `curl -s http://localhost:8080/api/admin/restaurants/1/menu-items/100 --user admin@gmail.com:admin`
>
> #### create MenuItems for Restaurant 2
> `curl -s -X POST http://localhost:8080/api/admin/restaurants/2/menu-items  --user admin@gmail.com:admin -H 'Content-Type: application/json' -d '{"name":"idaho potatoes","price":280.99}'`
>
> #### update MenuItems 2 for Restaurant 1
> `curl -s -X PUT http://localhost:8080/api/admin/restaurants/1/menu-items/2 --user admin@gmail.com:admin -H 'Content-Type: application/json' -d '{"name":"lobio","price":210.50}'`
>
> #### delete MenuItems 2 for Restaurant 1
> `curl -s -X DELETE http://localhost:8080/api/admin/restaurants/1/menu-items/2  --user admin@gmail.com:admin`
>
> #### create Votes for Restaurant 3
> `curl -s -X POST http://localhost:8080/api/profile/votes --user user2@mail.ru:password2 -H 'Content-Type: application/json' -d '{"restaurantId": 3}'`
>
>  #### create Votes for Restaurant not found
> `curl -s -X POST http://localhost:8080/api/profile/votes --user user2@mail.ru:password2 -H 'Content-Type: application/json' -d '{"restaurantId": 100}'`
>
> #### get Restaurants with Votes today
> `curl -s http://localhost:8080/api/restaurants/with-votes/today --user user2@mail.ru:password2`
