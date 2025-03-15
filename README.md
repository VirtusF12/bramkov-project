
В директории ~/IdeaProjects/docker-compose.yml 
 
cd ~/IdeaProjects
docker-compose build (собрать все образы)
docker images (просмотреть созданные образы)
docker-compose up (запуск контейнеров)
docker-compose down (остановка контейнеров)

В секции build в файле docker-compose.yml находится путь к расположению Dockerfile

- команды docker-compose вызывать из директории где находится сам файл настроек docker-compose.yml
:> ./up-microservices
:> docker-compose down

************************** HOSTS **************************
eureka-server: http://localhost:8761/
eureka-server (actuator): http://localhost:8761/actuator

************************** UP **************************
1. сначала нужно поднять eureka-service а потом все остальные сервисы 



В директории ~/IdeaProjects/docker-compose.yml

cd ~/IdeaProjects
docker-compose build (собрать все образы)
docker images (просмотреть созданные образы)
docker-compose up (запуск контейнеров)
docker-compose down (остановка контейнеров)

В секции build в файле docker-compose.yml находится путь к расположению Dockerfile


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
version: "3"

services:

exchangerate-service:
build: ~/IdeaProjects/exchangerate-service
ports:
- 8081:8081

bramkov-service:
build: ~/IdeaProjects/Bramkov
ports:
- 8080:8080


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
1. собрать проект, чтобы получить .jar файл (директория ~/IdeaProjects/project_name/target)
   cd ~/IdeaProjects/Bramkov
   mvn clean
   mvn package
2. создать Dockerfile
   FROM openjdk:23
   WORKDIR /app
   EXPOSE 8080
   COPY target/Bramkov-0.0.1-SNAPSHOT.jar app.jar
   ENTRYPOINT ["java", "-jar", "app.jar"]
3. создать образ на основе Dockerfile
   cd ~/IdeaProjects/Bramkov
   docker build -t bramkov . (docker build -t your-image-name .)
   docker images
4. запустить контейнер на основе образа
   docker container run -p 8080:8080 bramkov

- посмотреть все образы: docker images
- посмотреть контейнеры: docker container ls -a
- перед удалением образа необходимо остановить контейнер: docker container stop container_id
- удаление контейнера: docker container rm container_id
- удаление образа: docker image rm image_id

5. остановка контейнера
   docker container ls -a
   docker container stop container_id (docker container stop 1786a3b2f81b)
6. запуск контейнера
   docker container start container_id (docker container start 1786a3b2f81b)


***************** DOCKER COMPOSE *****************

Для запуска контейнеров автоматические - необходимо создать файл docker-compose.yml

1. перейти в директорию где находится файл docker-compose.yml
   cd ~/IdeaProjects/Bramkov
2. выполнить команду
   docker-compose build
3. запустить контейнеров
   docker-compose up
4. остановка контейнеров и освобождение ресурсов
   docker-compose down
5. список контейнеров
   docker-compose ps 

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
FROM openjdk:23

WORKDIR /app

COPY target/Bramkov-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
