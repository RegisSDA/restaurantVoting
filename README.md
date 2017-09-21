# restaurantVoting


REST API Документация

[Интерфейс ADMIN - rest/v1/admin](#admin)  

[Все рестараны](#rest_getAll)  
[Получить ресторан](#rest_get)  
[Удалить ресторан](#rest_del)  
[Сохранить/обновить ресторан](#rest_save)

[Интерфейс USER - rest/v1/user](#user)  

[История своих голосов](#get_votes)  
[Посмотреть голос](#get_vote)  
[Создать/обновить голос](#save_vote)  
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
Content: [{ name : "restaurant_1" },{ name : "restaurant_2"}]  
Error Response:  
Sample Call:  curl -s http://localhost:8080/rest/v1/admin/restaurants  
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
Content:{error : "Not Found"}
Sample Call: curl -s http://localhost:8080/rest/v1/admin/restaurants/testrest1
Notes:  


<a name="rest_del"><h3>Удалить рестаран</h3></a>

URL: /restaurants/:name  
Method: DELETE  
URL Params: no  
Data Params: no  
Success Response:  
Code: 204  
Error Response:  
Sample Call:  
Notes:  


<a id="rest_save"><h3>Сохранить/обновить ресторан</h3></a>

URL: /restaurants  
Method: POST  
URL Params: no  
Data Params: JSON  
{name : "restaurant", dishes : [{name : "dish1", price : 1000},{name : "dish2", price : 900}]}  
Success Response:  
Code: 201  
Error Response:  
Sample Call:  
Notes:


<a id="user"><h2>Интерфейс для USER</h2></a>
Базовый URL - rest/v1/user

<a id="get_votes"><h3>История своих голосов</h3></a>

URL: /votes  
Method: GET  
URL Params: no  
Data Params: no  
Success Response:  
Code: 200  
[ {"restaurant": "testrest1","date": "2017-09-10"},{"restaurant": "testrest3","date": "2017-09-11"}]  
Error Response:  
Sample Call:  
Notes:

<a id="get_vote"><h3>Посмотреть свой голос</h3></a>

URL: /votes/:data  
Method: GET  
URL Params: no  
Data Params: no  
Success Response:  
Code: 200  
{"restaurant": "testrest1","date": "2017-09-10"}  
Error Response:  
Code: 404  
Content: { error : "Not found" }  
Sample Call:  
Notes:

<a id="post_vote"><h3>Создать/обновить голос</h3></a>

URL: /votes  
Method: POST  
URL Params: no  
Data Params: JSON  
{"restaurant": "testrest1","date": "2017-09-10"}  
Success Response:  
Code: 201  
Error Response:  
Code: 403  
Content: { error : "Voting is over at 11:00" }  
Sample Call:  
Notes: Полосование доступно до 11:00 по времени сервера, до этого срока возможно свободно менять голос, после 11 голоса не принимаются

<a id="del_vote"><h3>Удалить голос</h3></a>

URL: /votes  
Method: DELETE  
URL Params: no  
Data Params: no  
Success Response:  
Code: 204  
Error Response:  
Code: 403  
Content: { error : "Voting is over at 11:00" }  

Sample Call:  
Notes: Отказаться можно только от голоса за текщую дату до 11:00 по времени сервера 

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
Sample Call:  
Notes: При отсутствии даты - возвращает за сегодняшний день


<a id="get_rest_with_menu"><h3>Рестораны с меню</h3></a>
Получить все имеющиеся рестораны с меню  
URL: /restaurants  
Method: GET  
URL Params: no  
Data Params: no  
Success Response:  
Code: 200  
[{"name":"testrest1","dishes":[{"name":"dish1","price":1000},{"name":"dish2","price":900},{"name":"dish3","price":800}]},{"name":"testrest2","dishes":[{"name":"dish2","price":1000},{"name":"dish3","price":1100},{"name":"dish4","price":1200}]},{"name":"testrest3","dishes":[{"name":"dish3","price":500},{"name":"dish4","price":600},{"name":"dish5","price":400}]}]  
Error Response:  
Sample Call:  
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
{"name":"testrest1","dishes":[{"name":"dish1","price":1000},{"name":"dish2","price":900},{"name":"dish3","price":800}]}  
Error Response:  
Sample Call:  
Notes:  
-При отсутствии даты - используется текущая дата   
-При одинаковом числе голосов возвращается первый по алфавиту