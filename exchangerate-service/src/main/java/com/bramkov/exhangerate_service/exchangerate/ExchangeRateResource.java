package com.bramkov.exhangerate_service.exchangerate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exchange")
public class ExchangeRateResource {

    private ExchangeRate exchangeRate = new ExchangeRate();

    /*
        http://localhost:8081/api/v1/exchange/rate
    */
    @GetMapping("/rate")
    public ExchangeRate exchangeRate() {

        return exchangeRate;
    }
}
