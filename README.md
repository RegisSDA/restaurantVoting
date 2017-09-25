# Restaurant Voting System

[Описание](#description)   
[REST API](#rest)  
[ТЗ](#tz)

<a name="description"><h2>Описание</h2></a>

Система выбора ресторана для обеда. 
Пользователям с ролью ADMIN доступно управлять ресторанами и пользователями. Пользователям с ролью USER доступно управлять своими голосами и получать информацию по результатам голосования.
Голосование доступно до 11.00 по времени сервера. Пользователь может иметь обе роли.  

Использованные технологии:  
- Работа с БД: H2, Spring Data-JPA(Hibernate)
- Web: Spring MVC, Spring Security (basic auth), реализован REST API, JSON(Jackson)
- Разное: JAVA 8, Spring (IoC, Test, Cache(ehcache)) , Maven, JUnit, SLF4J(logback)  

<a name="rest"><h2>REST API</h2></a>

[Интерфейс ADMIN - rest/v1/admin](#admin)  

[Все рестараны](#rest_getAll)  
[Получить ресторан](#rest_get)  
[Создать ресторан](#rest_save)  
[Обновить ресторан](#rest_update)  
[Удалить ресторан](#rest_del) 

[Все пользователи](#get_users)  
[Получить пользователя](#get_user)  
[Создать пользователя](#post_user)  
[Обновть пользователя](#put_user)  
[Удалить пользователя](#del_user)  

[Интерфейс USER - rest/v1/user](#user)  

[История своих голосов](#get_votes)  
[Посмотреть голос](#get_vote)  
[Создать голос](#post_vote)   
[Обновить голос](#put_vote)  
[Удалить голос](#del_vote)  
[Результаты голосования](#get_rest_with_votes)  
[Рестораны с меню](#get_rest_with_menu)  
[Лидирующий ресторан](#get_top1)  

<a name="admin"><h2>Интерфейс ADMIN</h2></a>
Базовый URL - - rest/v1/admin
<a name="rest_getAll"><h3>Все рестараны</h3></a>

URL: /restaurants  
Method: GET  
URL Params: no  
Data Params: no  
Success Response:  
Code: 200  
Content: [{"name":"testrest1"},{"name":"testrest2"},{"name":"testrest3"}]   
Error Response:  
Sample Call:  curl -u testuser1:testuser1 http://localhost:8080/restaurantVoting/rest/v1/admin/restaurants  
Notes: Рестораны содержат только название

<a name="rest_get"><h3>Получить ресторан</h3></a>

URL: /restaurants/:name  
Method: GET  
URL Params: no  
Data Params: no  
Success Response:  
Code: 200  
Content: {"name":"testrest1","dishes":[{"name":"dish1","price":1000},{"name":"dish2","price":900},{"name":"dish3","price":800}]}  
Error Response:  
Code 404  
Sample Call: curl -u testuser1:testuser1 http://localhost:8080/restaurantVoting/rest/v1/admin/restaurants/testrest1  
Notes:  


<a id="rest_save"><h3>Создать ресторан</h3></a>

URL: /restaurants  
Method: POST  
URL Params: no  
Data Params: JSON  
{name : "restaurant", dishes : [{name : "dish1", price : 1000},{name : "dish2", price : 900}]}  
Success Response:  
Code: 201  
Error Response:  
400  
409  
Sample Call:  
for windows: curl -u testuser1:testuser1 -X POST -H "Content-Type: application/json" -d "{\"name\":\"testrest4\",\"dishes\":[{\"name\":\"dish1\",\"price\":1000},{\"name\":\"dish2\",\"price\":900},{\"name\":\"dish3\",\"price\":800}]}"  http://localhost:8080/restaurantVoting/rest/v1/admin/restaurants  
for linux: curl -u testuser1:testuser1 -X POST -H "Content-Type: application/json" -d '{"name":"testrest4","dishes":[{"name":"dish1","price":1000},{"name":"dish2","price":900},{"name":"dish3","price":800}]}"  http://localhost:8080/restaurantVoting/rest/v1/admin/restaurants  
Notes:

<a id="rest_update"><h3>Обновить ресторан</h3></a>

URL: /restaurants/:name  
Method: PUT  
URL Params: no  
Data Params: JSON  
{name : "restaurant", dishes : [{name : "dish1", price : 1000},{name : "dish2", price : 900}]}  
Success Response:  
Code: 200  
Error Response:  
400 
Sample Call:  
for windows: curl -u testuser1:testuser1 -X PUT -H "Content-Type: application/json" -d "{\"name\":\"testrest2\",\"dishes\":[{\"name\":\"dish1\",\"price\":1000},{\"name\":\"dish2\",\"price\":900},{\"name\":\"dish3\",\"price\":800}]}"  http://localhost:8080/restaurantVoting/rest/v1/admin/restaurants/testrest2  
for linux: curl -u testuser1:testuser1 -X PUT -H "Content-Type: application/json" -d '{"name":"testrest2","dishes":[{"name":"dish1","price":1000},{"name":"dish2","price":900},{"name":"dish3","price":800}]}"  http://localhost:8080/restaurantVoting/rest/v1/admin/restaurants/testrest2   
Notes:


<a name="rest_del"><h3>Удалить рестаран</h3></a>

URL: /restaurants/:name  
Method: DELETE  
URL Params: no  
Data Params: no  
Success Response:  
Code: 204  
Error Response:  
Sample Call:  curl -u testuser1:testuser1 -X DELETE http://localhost:8080/restaurantVoting/rest/v1/admin/restaurants/testrest4  
Notes:  


<a name="get_users"><h3>Все пользователи</h3></a>

URL: /users  
Method: GET  
URL Params: no  
Data Params: no  
Success Response:  
[{"login":"testuser1","password":"testuser1","roles":["ROLE_USER","ROLE_ADMIN"]},{"login":"testuser2","password":"testuser2","roles":["ROLE_ADMIN"]},{"login":"testuser3","password":"testuser3","roles":["ROLE_USER"]}]  
Code: 200  
Error Response:  
Sample Call:  curl -u testuser1:testuser1 http://localhost:8080/restaurantVoting/rest/v1/admin/users    
Notes:  

<a name="get_user"><h3>Получить пользователя</h3></a>

URL: /users/:login  
Method: GET  
URL Params: no  
Data Params: no  
Success Response:  
{"login":"testuser1","password":"testuser1","roles":["ROLE_USER","ROLE_ADMIN"]}  
Code: 200  
Error Response:  
Sample Call:  curl -u testuser1:testuser1 http://localhost:8080/restaurantVoting/rest/v1/admin/users/testuser1      
Notes: 


<a name="post_user"><h3>Создать пользователя</h3></a>

URL: /users  
Method: POST  
URL Params: no  
Data Params: JSON  
{login:"testuser1",password:"testuser1",roles:["ROLE_USER","ROLE_ADMIN"]}
Success Response:   
Code: 201  
Error Response:  
409  
Sample Call:  
for windows: curl -u testuser1:testuser1 -X POST -H "Content-Type: application/json" -d "{\"login\":\"testuserCreated\",\"password\":\"testuser1\",\"roles\":[\"ROLE_USER\",\"ROLE_ADMIN\"]}"  http://localhost:8080/restaurantVoting/rest/v1/admin/users    
for linux: curl -u testuser1:testuser1 -X POST -H "Content-Type: application/json" -d "{"login""testuserCreated","password":"testuser1","roles":["ROLE_USER","ROLE_ADMIN"]}"  http://localhost:8080/restaurantVoting/rest/v1/admin/users  
Notes:


<a name="put_user"><h3>Обновить пользователя</h3></a>

URL: /users/:login  
Method: PUT  
URL Params: no  
Data Params: JSON  
{login:"testuser1",password:"testuser1",roles:["ROLE_USER","ROLE_ADMIN"]}
Success Response:   
Code: 200  
Error Response:  
404, 403    
Sample Call:  
for windows: curl -u testuser1:testuser1 -X PUT -H "Content-Type: application/json" -d "{\"login\":\"testuser3\",\"password\":\"updatedPass\",\"roles\":[\"ROLE_USER\",\"ROLE_ADMIN\"]}"  http://localhost:8080/restaurantVoting/rest/v1/admin/users/testuser3    
for linux: curl -u testuser1:testuser1 -X PUT -H "Content-Type: application/json" -d "{"login""testuser3","password":"updatedPass","roles":["ROLE_USER","ROLE_ADMIN"]}"  http://localhost:8080/restaurantVoting/rest/v1/admin/users/testuser3  
Notes: Можно обновить только пользователей не имеющих роли ADMIN или себя


<a name="put_user"><h3>бновить пользователя</h3></a>

URL: /users/:login  
Method: DELETE  
URL Params: no  
Data Params: no  
Success Response:   
Code: 204  
Error Response:  
404, 403    
Sample Call:  
for windows: curl -u testuser1:testuser1 -X DELETE  http://localhost:8080/restaurantVoting/rest/v1/admin/users/testuser1    
for linux: curl -u testuser1:testuser1 -X DELETE   http://localhost:8080/restaurantVoting/rest/v1/admin/users/testuser1  
Notes: Можно удалить только пользователей не имеющих роли ADMIN или себя


<a id="user"><h2>Интерфейс для USER</h2></a>
Базовый URL - rest/v1/user

<a id="get_votes"><h3>История своих голосов</h3></a>

URL: /votes  
Method: GET  
URL Params: no  
Data Params: no  
Success Response:  
Code: 200  
[{"id":3,"restaurant":"testrest2","date":"2017-09-10"},{"id":6,"restaurant":"testrest2","date":"2017-09-11"}]     
Error Response:  
Sample Call:  curl -u testuser3:testuser3 http://localhost:8080/restaurantVoting/rest/v1/user/votes
Notes:

<a id="get_vote"><h3>Посмотреть свой голос</h3></a>

URL: /votes/:data  
Method: GET  
URL Params: no  
Data Params: no  
Success Response:  
Code: 200  
{"id":3,"restaurant":"testrest2","date":"2017-09-10"}  
Error Response:  
Code: 404   
Sample Call:  curl -u testuser3:testuser3 http://localhost:8080/restaurantVoting/rest/v1/user/votes/2017-09-10  
Notes:

<a id="post_vote"><h3>Создать голос</h3></a>

URL: /votes  
Method: POST  
URL Params: no  
Data Params: JSON  
{"id":"null","restaurant":"testrest2","date":"2017-09-10"}   
Success Response:  
Code: 201  
Error Response:  
Code:  403, 409  
Sample Call:  
for windows: curl -u testuser1:testuser1 -X POST -H "Content-Type: application/json" -d "{\"id\":\"null\",\"restaurant\":\"testrest2\",\"date\":\"2017-09-25\"}"  http://localhost:8080/restaurantVoting/rest/v1/user/votes  
for linux: curl -u testuser1:testuser1 -X POST -H "Content-Type: application/json" -d "{"id":"null","restaurant":"testrest2","date":"2017-09-25"}'  http://localhost:8080/restaurantVoting/rest/v1/user/votes   
Notes: Полосование доступно до 11:00 по времени сервера, до этого срока возможно свободно менять голос, после 11 голоса не принимаются. Голоса принимаются только за текущий день.  

<a id="put_vote"><h3>Обновить голос</h3></a>

URL: /votes/:id  
Method: PUT  
URL Params: no  
Data Params: JSON  
{"id":"1","restaurant":"testrest2","date":"2017-09-10"}   
Success Response:  
Code: 200  
Sample Call:  
for windows: curl -u testuser1:testuser1 -X PUT -H "Content-Type: application/json" -d "{\"id\":1,\"restaurant\":\"testrest2\",\"date\":\"2017-09-25\"}"  http://localhost:8080/restaurantVoting/rest/v1/user/votes/1  
for linux: curl -u testuser1:testuser1 -X PUT -H "Content-Type: application/json" -d "{"id":1,"restaurant":"testrest2","date":"2017-09-25"}'  http://localhost:8080/restaurantVoting/rest/v1/user/votes/1   
Error Response:  
Code:  403, 409  
Sample Call:  
Notes: Голосование доступно до 11:00 по времени сервера, до этого срока возможно свободно менять голос, после 11 голоса не принимаются. Голоса принимаются только за текущий день.


<a id="del_vote"><h3>Удалить голос</h3></a>

URL: /votes  
Method: DELETE  
URL Params: no  
Data Params: no  
Success Response:  
Code: 204  
Error Response:  
Code: 403  
Sample Call:  curl -u testuser1:testuser1 -X DELETE http://localhost:8080/restaurantVoting/rest/v1/user/votes/7  
Notes: Отказаться можно только от голоса за текщую дату до 11:00 по времени сервера (Для тестирования предварительно создайте голос за сегодняшнюю дату)

<a id="get_rest_with_votes"><h3>Результаты голосования</h3></a>
Получить список ресторанов с кол-вом голосов за каждый  
URL: /restaurants/votes  
Method: GET  
URL Params:  
 optional:
 date=[ISO DATE]  
Data Params: no  
Success Response:  
Code: 200  
[{"name":"testrest3","votes":1}]  
Error Response:  
Sample Call:  curl -u testuser1:testuser1 http://localhost:8080/restaurantVoting/rest/v1/user/restaurants/votes?date=2017-09-11  
Notes: При отсутствии даты - возвращает за сегодняшний день


<a id="get_rest_with_menu"><h3>Рестораны с меню</h3></a>
Получить все имеющиеся рестораны с меню  
URL: /restaurants  
Method: GET  
URL Params: no  
Data Params: no  
Success Response:  
Code: 200  
[{"name":"testrest2","votes":1},{"name":"testrest3","votes":2}]  
[{"name":"testrest1","dishes":[{"name":"dish1","price":1000},{"name":"dish2","price":900},{"name":"dish3","price":800}]},{"name":"testrest2","dishes":[{"name":"dish1","price":1000},{"name":"dish2","price":900},{"name":"dish3","price":800}]},{"name":"testrest3","dishes":[{"name":"dish3","price":500},{"name":"dish4","price":600},{"name":"dish5","price":400}]}]    
Error Response:  
Sample Call: curl -u testuser1:testuser1 http://localhost:8080/restaurantVoting/rest/v1/user/restaurants  
Notes:
 
<a id="get_top1"><h3>Лидирующий ресторан</h3></a>
Получить ресторан с наибольшим числом голосов   
URL: /restaurants/top1    
Method: GET  
URL Params:  
optional:
date=[ISO DATE]    
Data Params: no  
Success Response:  
Code: 200  
{"name":"testrest3","dishes":[{"name":"dish3","price":500},{"name":"dish4","price":600},{"name":"dish5","price":400}]}    
Error Response:  
Sample Call:   curl -u testuser1:testuser1 http://localhost:8080/restaurantVoting/rest/v1/user/restaurants/top1?date=2017-09-11  
Notes:  
-При отсутствии даты - используется текущая дата   
-При одинаковом числе голосов возвращается первый по алфавиту



<a name="tz"><h2>ТЗ</h2></a>

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

The task is:

Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users
Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
Menu changes each day (admins do the updates)
Users can vote on which restaurant they want to have lunch at
Only one vote counted per user
If user votes again the same day:
If it is before 11:00 we assume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides new menu each day.