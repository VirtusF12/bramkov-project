package com.bramkov.file_microservice.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")
public class IndexController {

    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${spring.application.name}")
    private String nameProject;

    @GetMapping
    public String index(Model model) {

        System.out.println(String.format("baseUrl = %s", baseUrl));

        model.addAttribute("nameProject", nameProject);

        return "index";
    }
}
