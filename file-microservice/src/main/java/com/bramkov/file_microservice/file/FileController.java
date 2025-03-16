package com.bramkov.file_microservice.file;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileController {

    @GetMapping
    public String uploadPage(Model model) {
        model.addAttribute("nameProject","Загрузка файлов");
        return "index";
    }

}