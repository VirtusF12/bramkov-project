
**************** Postgres DataBase: bramkov_db ****************
1. (открыть под vpn) https://www.bezkoder.com/spring-boot-postgresql-example/
2. (урок Гоша Дударь) https://www.youtube.com/watch?v=oGK2KufvxM0&list=PL0lO_mIqDDFUYDRzvocu5EsFGBqPM7CIw&index=4


**************** API ****************
These are APIs that we need to provide:

Methods             Urls                                Actions
  POST	        /api/articles	                create new Tutorial
  GET	        /api/articles	                retrieve all Tutorials
  GET	        /api/articles/:id	            retrieve a Tutorial by :id
  PUT	        /api/articles/:id	            update a Tutorial by :id
 DELETE	        /api/articles/:id	            delete a Tutorial by :id
 DELETE	        /api/articles	                delete all Tutorials
  GET	        /api/articles/published	    find all published Tutorials
  GET	        /api/articles?title=[keyword]	find all Tutorials which title contains key

As you have mentioned @PatchMapping for updates then you need the HTTP PATCH method instead of POST. The same goes for delete you have used @DeleteMapping, so you need to use the HTTP DELETE method instead of POST.

Create -> POST
Read   -> GET
Update -> PUT/PATCH
Delete -> DELETE
With form submit I don't think PATCH/PUT/DELETE will work so in that case you need to change @PatchMapping/@DeleteMapping to @PostMapping and update the URL so it will be unique for update/delete.

PUT/PATCH/DELETE will work with REST API only.


https://habr.com/ru/articles/251193/
https://www.thoughtworks.com/insights/blog/rest-api-design-resource-modeling
https://javarush.com/groups/posts/3139-spring---ehto-ne-strashno-prosloyka-iz-dto
https://habr.com/ru/articles/513072/