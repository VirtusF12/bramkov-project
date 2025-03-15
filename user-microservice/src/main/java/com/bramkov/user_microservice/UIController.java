package com.bramkov.user_microservice;

import com.bramkov.user_microservice.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
    baseurl:        http://127.0.0.1:8089/
    microservice:   user-microservice
    database:       user_microservice_db
*/
//@Controller
//@RequestMapping("/")
//public class UIController {
//
//    @GetMapping()
//    public String index(Model model) {
//
//        model.addAttribute("nameMicroservice", "USER-MICROSERVICE");
//        model.addAttribute("user", new User());
//
//        return "index";
//    }
//
//    @GetMapping("success")
//    public String success() {
//
//        return "success";
//    }
//}
