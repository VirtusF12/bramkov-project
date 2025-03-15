1. собрать проект, чтобы получить .jar файл (директория ~/IdeaProjects/project_name/target)
    cd ~/IdeaProjects/exchangerate-service
    mvn clean
    mvn package
2. создать Dockerfile 
    FROM openjdk:23
    WORKDIR /app
    EXPOSE 8081
    COPY ~/IdeaProjects/exchangerate-service/target/exchangerate-service-0.0.1-SNAPSHOT.jar app.jar 
    ENTRYPOINT ["java", "-jar", "app.jar"]
3. создать образ на основе Dockerfile
    cd ~/IdeaProjects/exchangerate-service
    docker build -t exchangerate_service . (docker build -t your-image-name .)
    docker images
4. запустить контейнер на основе образа
    docker container run -p 8081:8081 exchangerate-service

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