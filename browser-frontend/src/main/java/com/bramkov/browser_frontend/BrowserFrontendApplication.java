package com.bramkov.browser_frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
    @baseurl:        http://127.0.0.1:8085/
    @microservice:   -
    @database:       -
*/
@SpringBootApplication
//@EnableCaching
public class BrowserFrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrowserFrontendApplication.class, args);
	}
}
