# Dockerfile необходим для создания образа
# docker build -t your-image-name .
# docker container run -p 8081:8081 your-image-name


# использовать официальный образ java
FROM openjdk:23

# установка рабочей директории
WORKDIR /app

# копирование jar файла в образ
COPY target/exchangerate-service-0.0.1-SNAPSHOT.jar app.jar

# приложение будет запущено на порту 8080
EXPOSE 8081

# команда для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]