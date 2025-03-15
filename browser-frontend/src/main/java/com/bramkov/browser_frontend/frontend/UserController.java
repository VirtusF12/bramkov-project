package com.bramkov.browser_frontend.frontend;

import com.bramkov.browser_frontend.user.Gender;
import com.bramkov.browser_frontend.user.User;
import com.bramkov.browser_frontend.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/edit")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Value("${project.path.icon:Icon}")
    private String pathIcon;

    @GetMapping()
    public String edit(
            Model model
    ) {
        model.addAttribute("pathIcon", pathIcon);
        model.addAttribute("title", "Редактирование профиля");
        model.addAttribute("projectName", "Редактирование профиля");
        model.addAttribute("user", new User());
        return "edit/edit";
    }

    @GetMapping("/description")
    public String description(
            Model model
    ) {
        model.addAttribute("pathIcon", pathIcon);
        model.addAttribute("title", "Описание");
        model.addAttribute("projectName", "Описание");
        model.addAttribute("user", new User());
        return "edit/description";
    }

    @PostMapping("/description")
    public String updateDescription(
            @ModelAttribute("user") User user
    ) {
        log.info("""
                user.getDescription() = {}
                """,
                user.getDescription());
        userService.updateUser(user, "description");
        return "redirect:/home";
    }

    @GetMapping("/dob")
    public String dob(
            Model model
    ) {
        model.addAttribute("pathIcon", pathIcon);
        model.addAttribute("title", "День Рождения");
        model.addAttribute("projectName", "День Рождения");
        model.addAttribute("user", new User());
        return "edit/dob";
    }

    @PostMapping("/dob")
    public String updateDOB(
            @ModelAttribute("user") User user
    ) {
        log.info("""
                user.getDob() = {}
                """,
                user.getDob());
        userService.updateUser(user, "dob");
        return "redirect:/home";
    }

    @GetMapping("/name")
    public String name(
            Model model
    ) {
        model.addAttribute("pathIcon", pathIcon);
        model.addAttribute("title", "Имя");
        model.addAttribute("projectName", "Имя");
        model.addAttribute("user", new User());
        return "edit/name";
    }

    @PostMapping("/name")
    public String updateName(
            @ModelAttribute("user") User user
    ) {
        log.info("""
                user.getName() = {}
                """,
                user.getName());
        userService.updateUser(user, "name");
        return "redirect:/home";
    }

    @GetMapping("/gender")
    public String gender(
            Model model
    ) {
        model.addAttribute("pathIcon", pathIcon);
        model.addAttribute("title", "Пол");
        model.addAttribute("projectName", "Пол");
        return "edit/gender";
    }

    @PostMapping("/gender")
    public String updateGender(
            @RequestParam(name = "gender") String gender
    ) {
        User user = new User();
        user.setGender(gender.equals("MAN") ? Gender.MALE : Gender.FEMALE);
        userService.updateUser(user, "gender");
        return "redirect:/home";
    }

    @GetMapping("/photo")
    public String photo(
            Model model
    ) {
        model.addAttribute("pathIcon", pathIcon);
        model.addAttribute("title", "Фотография");
        model.addAttribute("projectName", "Фотография");
        model.addAttribute("user", new User());
        return "edit/photo";
    }

    @GetMapping("/profile")
    public String profile(
            Model model
    ) {
        model.addAttribute("pathIcon", pathIcon);
        model.addAttribute("title", "Профиль");
        model.addAttribute("projectName", "Профиль");
        model.addAttribute("user", new User());
        return "edit/profile";
    }
}
