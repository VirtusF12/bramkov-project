package com.bramkov.b_service;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class BServiceRestController {

    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    public BServiceRestController(DiscoveryClient discoveryClient, RestClient.Builder restClientBuilder) {
        this.discoveryClient = discoveryClient;
        restClient = restClientBuilder.build();
    }

    @GetMapping("helloEureka")
    public String hello() {
        ServiceInstance serviceInstance = discoveryClient.getInstances("a-service").get(0);
        String serviceAResponse = restClient.get()
                .uri(serviceInstance.getUri() + "/hello")
                .retrieve()
                .body(String.class);
        return serviceAResponse;
    }
}
