package com.bramkov.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;


@Configuration
public class GatewayConfig {

    /*
        (request) -> gateway: http://localhost:8080 -> http://localhost:8080/api/v1/exchange/rate ->
        -> http://192.168.1.2:8087/api/v1/exchange/rate
        eureka-server: http://localhost:8761/
    */
    @Bean
    RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()

                .route("exchangerate_service_route",
                        route -> route.path("/api/v1/exchange/rate")
                                .and()
                                .method(HttpMethod.GET)
                                .uri("http://192.168.1.2:8087"))

                .route("a_service_route", route -> route.path("/hello")
                        .and()
                        .method(HttpMethod.GET)
                        .uri("http://192.168.1.2:8085"))

                .route("b_service_route", route -> route.path("/**")
                        .and()
                        .method(HttpMethod.GET)
                        .uri("http://192.168.1.2:8086"))

                .build();
    }
}
