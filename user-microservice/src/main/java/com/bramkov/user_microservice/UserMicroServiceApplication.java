package com.bramkov.user_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    baseurl:        http://127.0.0.1:8089/
    microservice:   user-microservice
    database:      	user_microservice_db
*/
@SpringBootApplication
public class UserMicroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMicroServiceApplication.class, args);
	}

}
