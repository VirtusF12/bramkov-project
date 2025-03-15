package com.bramkov.browser_frontend.frontend;

import com.bramkov.browser_frontend.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClient;

/**
    @baseurl:        http://127.0.0.1:8085/
    @microservice:   -
    @database:       -
*/
@Controller
@RequestMapping(path = "/")
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    @Value("${project.path.icon:Icon}")
    private String pathIcon;

    @Value("${spring.application.name}")
    private String nameProject;

    @Value("${endpoint.service.exchange.rate}")
    private String serviceId;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public IndexController(DiscoveryClient discoveryClient, RestClient.Builder restClientBuilder) {
        this.discoveryClient = discoveryClient;
        restClient = restClientBuilder.build();
    }

    private String getCourse(String json)  {

        String result = null;

        try {
            Object obj = new JSONParser().parse(json);
            JSONArray currencyList = (JSONArray) ((JSONObject) obj).get("currencyList");

            for (Object o : currencyList) {

                JSONObject e = (JSONObject) o;

                String code = e.get("code").toString();
                String nameRate = e.get("nameRate").toString();
                String course = e.get("course").toString();
                String lateCode = e.get("lateCode").toString();

                System.out.println(String.format("%s:%s:%s:%s",code,nameRate,course,lateCode));

                if (code.equals("840"))
                    result = String.format("%s:%s:%s:%s",code,nameRate,course,lateCode);
            }

        } catch (ParseException e) {
            return null;
        }

        return result;
    }

    @GetMapping("")
    public String index(Model model) {

        model.addAttribute("title", "Главная страница");
        model.addAttribute("pathIcon", pathIcon);
        model.addAttribute("nameProject", nameProject);

        try {
            ServiceInstance serviceInstance = discoveryClient.getInstances(serviceId).get(0);

            String response = restClient.get()
                    .uri(serviceInstance.getUri() + "/api/v1/exchange/rate")
                    .retrieve()
                    .body(String.class);

            if (response != null) {
                model.addAttribute("course", getCourse(response));
            } else {
                model.addAttribute("data", String.format("Data not found from serviceId = %s",serviceId));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        log.info("GET: /");
        return "index";
    }

    @GetMapping("login")
    public String login(Model model) {

        model.addAttribute("title", "Логин");
        model.addAttribute("pathIcon", pathIcon);
        log.info("GET: /login");

        return "login";
    }

    @PostMapping("login")
    public String login() {

        log.info("POST: /login");

        return "redirect:/home";
    }

    @GetMapping("reg")
    public String reg(@ModelAttribute("user") User user,
                      Model model) {

        model.addAttribute("title",String.format("Регистрация пользователя - %s", nameProject));
        model.addAttribute("pathIcon", pathIcon);

        return "reg";
    }

    @PostMapping("reg")
    public String regNewUser(@ModelAttribute("user") User user,
                      Model model) {

        log.info("POST: regNewUser(user) -> username: {}, password: {}, passwordConfirm: {}",
                user.getUsername(),
                user.getPassword(),
                user.getPasswordConfirm());

        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("isShowErrorMessageByPassword", true);
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "reg";
        }

        /**
         * @POST: user-microservice (create new user)
         */
        ServiceInstance serviceInstance = discoveryClient.getInstances("user-microservice").get(0);
        final String username = user.getUsername();
        final String password = passwordEncoder.encode(user.getPassword());
        final String body = """
                {
                            "username": "%s",
                            "password": "%s",
                            "passwordConfirm": "%s",
                            "gender": null,
                            "name": null,
                            "surname": null,
                            "description": null,
                            "email": null,
                            "phone": null,
                            "dob": null,
                            "lastVisit": null,
                            "roles": [  {
                                            "name": "USER"  \s
                                        }
                                     ]
                        }
                """
                .formatted(
                        username,
                        password,
                        password
                );
        log.info("POST: regNewUser(user) body -> {}", body);

        String response = restClient.method(HttpMethod.POST)
                .uri(serviceInstance.getUri() + "/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(String.class);

        log.info("POST: regNewUser(user) -> response: {}",
                            response);

        return "redirect:/login";
    }

    @GetMapping("home")
    public String home(HttpServletRequest request,
                       HttpServletResponse response,
                       Model model) {

        model.addAttribute("pathIcon", pathIcon);
        model.addAttribute("nameProject", nameProject);

        // https://www.baeldung.com/get-user-in-spring-security

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }

        log.error("GET: home -> auth.getName() {}", auth.getName());

        return "home";
    }
}