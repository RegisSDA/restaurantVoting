# restaurantVoting


REST API Документация


<a id=Удалить ресторан">Удалить ресторан</a>

Интерфейс ADMIN - rest/v1/admin

Все рестараны 

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

Получить ресторан с меню

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


[hlink](#Удалить рестаран)Удалить рестаран

URL: /restaurants/:name

Method: DELETE
 
URL Params: no

Data Params: no

Success Response:

Code: 200 
Error Response:
Sample Call:
Notes:


Сохранить рестаран

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


Интерфейс для прользователей ( права "USER")
Базовый URL - rest/v1/user

Получить историю своих голосов

URL: /votes

Method: GET
 
URL Params: no

Data Params: 

Success Response:

Code: 200 
[ {"restaurant": "testrest1","date": "2017-09-10"},{"restaurant": "testrest3","date": "2017-09-11"}]
    
Error Response:
Sample Call:
Notes:

Получить голос за определенную дату

URL: /votes/:data

Method: GET
 
URL Params: no

Data Params: no

Success Response:

Code: 200 
{"restaurant": "testrest1","date": "2017-09-10"}
    
Error Response:
Sample Call:
Notes:

Создать голос
(проголосовать)

URL: /votes

Method: POST
 
URL Params: no

Data Params: JSON  
{"restaurant": "testrest1","date": "2017-09-10"}

Success Response:

Code: 200 
    
Error Response:
Sample Call:
Notes: Полосование доступно до 11:00 по времени сервера, до этого срока возможно свободно менять голос, после 11 голоса не принимаются

Удалить голос

URL: /votes/:date

Method: GET
 
URL Params: no

Data Params: 

Success Response:

Code: 200 
    
Error Response:
Sample Call:

Notes: Отказаться можно только от голоса за текщую дату до 11:00 по времени сервера 


Получить голоса за рестораны на текущий момент 

URL: /restaurants/votes

Method: GET
 
URL Params: no

Data Params: no

Success Response:

Code: 200 
    
Error Response:
Sample Call:

Notes: 



Получить список ресторанов с меню

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
 
 
Получить ресторан с наибольшим числом голосов

URL: /restaurants/votes

Method: GET
 
URL Params: no

Data Params: no

Success Response:

Code: 200 

{"name":"testrest1","dishes":[{"name":"dish1","price":1000},{"name":"dish2","price":900},{"name":"dish3","price":800}]}
    
Error Response:
Sample Call:

Notes: 