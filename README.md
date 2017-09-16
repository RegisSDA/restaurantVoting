# restaurantVoting


REST API Документация

[Интерфейс ADMIN - rest/v1/admin](#admin)  

[Все рестараны](#rest_getAll)  
[Получить ресторан](#rest_get)  
[Удалить ресторан](#rest_del)  
[Сохранить/Обновить ресторан](#rest_save)

[Интерфейс USER - rest/v1/user](#user)
[История голосов](#get_votes)  
[Голос за текущий день](#get_vote)  
[Сохранить голос](#save_vote)  
[Удалить голос](#del_vote)  
[Получить рейтинг на сегодня](#get_rest_with_votes)  
[Получить рестораны с меню](#get_rest_with_menu)  
[Получить лидирующий по голосования ресторан](#get_top1)  

<a name="admin"><h2>Интерфейс ADMIN - rest/v1/admin</h2></a>

<a name="rest_getAll"><h3>Все рестараны</h3></a>

Информация о всех имеющихся рестаранах (только названия, меню не передаются)

URL: /restaurants  
Method: GET  
URL Params: no  
Data Params: no  
Success Response:  
Code: 200  
Content: [{ name : "restaurant_1" },{ name : "restaurant_2"}]  
Error Response:
Sample Call:
Notes:

<a name="rest_get"><h3>Получить ресторан</h3></a>

URL: /restaurants/:name  
Method: GET  
URL Params: no  
Data Params: no  
Success Response:  
Code: 200  
Content: {name : "restaurant", dishes : [{name : "dish1"},{name : "dish2"}]}  
Error Response:
Sample Call:
Notes:


<a name="rest_del"><h3>Удалить рестаран</h3></a>

URL: /restaurants/:name  
Method: DELETE  
URL Params: no  
Data Params: no  
Success Response:  
Code: 200  
Error Response:
Sample Call:
Notes:


<a id="rest_save"><h3>Сохранить рестаран</h3></a>

URL: /restaurants  
Method: POST  
URL Params: no  
Data Params: JSON с рестораном с меню  
{name : "restaurant", dishes : [{name : "dish1"},{name : "dish2"}]}
Success Response:  
Code: 200  
Error Response:
Sample Call:
Notes:

<a id="user"><h2>Интерфейс для прользователей</h2></a>
Базовый URL - rest/v1/user

<a id="get_votes"><h3>Получить историю своих голосов</h3></a>

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

<a id="get_vote"><h3>Получить голос за определенную дату</h3></a>

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
Code: 200  
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
Code: 200  
Error Response:  
Code: 403  
Content: { error : "Voting is over at 11:00" }  

Sample Call:  
Notes: Отказаться можно только от голоса за текщую дату до 11:00 по времени сервера 

<a id="get_rest_with_votes"><h3>Получить голоса за рестораны на текущий момент </h3></a>

URL: /restaurants/votes  
Method: GET  
URL Params: no  
Data Params: no  
Success Response:  
Code: 200  
Error Response:
Sample Call:
Notes: 


<a id="get_rest_with_menu"><h3>Получить список ресторанов с меню</h3></a>

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
 
<a id="get_top1"><h3>Получить ресторан с наибольшим числом голосов</h3></a>

URL: /restaurants/top1    
Method: GET  
URL Params: no  
Data Params: no  
Success Response:  
Code: 200  
{"name":"testrest1","dishes":[{"name":"dish1","price":1000},{"name":"dish2","price":900},{"name":"dish3","price":800}]}  
Error Response:
Sample Call:
Notes: При одинаковом числе голосов возвращается 