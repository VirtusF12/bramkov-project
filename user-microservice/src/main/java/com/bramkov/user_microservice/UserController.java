package com.bramkov.user_microservice;

import com.bramkov.user_microservice.user.User;
import com.bramkov.user_microservice.user.UserRepository;
import com.bramkov.user_microservice.user.UserRestController;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
//@RequestMapping("/user")
//public class UserController {
//
//    private static final Logger log = LoggerFactory.getLogger(UserController.class);
//
//    private final UserRepository userRepository;
//
//    public UserController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @PostMapping("/add")
//    public String add(@ModelAttribute("user") @Valid User newUser, BindingResult bindingResult) {
//
//        if (bindingResult.hasErrors())
//            return "index";
//
//        System.out.println(String.format("username: %s",newUser.getUsername()));
//        System.out.println(String.format("password: %s",newUser.getPassword()));
//        System.out.println(String.format("passwordConfirm: %s",newUser.getPasswordConfirm()));
//
//        try {
//            userRepository.save(newUser);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//
//        return "redirect:/success";
//    }
//}
