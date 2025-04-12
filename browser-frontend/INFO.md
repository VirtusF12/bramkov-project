
<!--https://uxwing.com/svg-icon-editor/-->

mihail@MacBook browser-frontend % ./mvnw package
mihail@MacBook browser-frontend % java -jar target/browser-frontend-0.0.1-SNAPSHOT.jar

mihail@MacBook browser-frontend % ./mvnw springboot:run

// проверка успешной загрузки контекста приложения Spring  
mihail@MacBook browser-frontend % ./mvnw test



/**
@Docs Spring Security: https://docs.spring.io/spring-security/reference/getting-spring-security.html
*/

/**
* https://www.browserling.com/tools/bcrypt
*/

//"""
//                {
//                            "username": "%s",
//                            "password": "%s",
//                            "passwordConfirm": "%s",
//                            "gender": null,
//                            "name": null,
//                            "surname": null,
//                            "description": null,
//                            "email": null,
//                            "phone": null,
//                            "dob": null,
//                            "lastVisit": null,
//                            "roles": [  {
//                                            "name": "USER"  \s
//                                        },\s
//                                        {
//                                            "name": "ADMIN"  \s
//                                        }
//                                    ]
//                        }
//                """



// password: "$2a$10$MMbjWHW3ZuJfXeNq7i.fLOw5a3JnvwmDsxgiooXVzUP6fDhp78p9O"

//        user.setPathImg("/static/img/contact/default_img_contact.jpg");
//        user.setIndexCard(0);
//        user.setDateTimeRegistration(LocalDateTime.now());
//        Set<Long> likes = new HashSet<>();
//        user.setLikes(likes);

//        if (!userService.saveUser(user)) {
//            model.addAttribute("isShowErrorMessageByUsername", true);
//            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
//            return "reg";
//        }


  <!-- oauth2 github, google -->
<!--  <div class="container unauthenticated">-->
<!--    <div class="row justify-content-center">-->
<!--      <div class="col-3">-->
<!--        <a href="/oauth2/authorization/github">-->
<!--          <img src="/img/login/github.svg" alt="github" class="wh-32">-->
<!--        </a>-->
<!--      </div>-->
<!--      <div class="col-3">-->
<!--        <a href="/oauth2/authorization/google">-->
<!--          <img src="/img/login/google.svg" alt="google" class="wh-32">-->
<!--        </a>-->
<!--      </div>-->
<!--      <div class="col-3">-->
<!--        <a href="https://oauth.vk.com/authorize?client_id=51719014&display=page&redirect_uri=https://friend18.ru/callback&scope=email&response_type=code&v=5.131">-->
<!--          <img src="/img/login/vk.svg" alt="vk" class="wh-32">-->
<!--        </a>-->
<!--      </div>-->
<!--    </div>-->
<!--  </div>-->


<!-- <input  type="text"
          pattern="^[а-яА-Яa-zA-Z]+$"
          title="Имя"
          th:field="*{fullName}"
          placeholder="Имя"
          class="form-control input-reg"
          required />
  <br>
  <span>Дата рождения</span>
  <input  id="dob"
          type="date"
          placeholder="Дата рождения"
          class="form-control input-reg"
          th:field="*{dob}"
          required />
  <br>
  <label>Ваш пол</label>
  <div style="text-align: left; margin-top: 10px;">
    <input  id="man"
            type="radio"
            th:field="*{gender}"
            name="gender"
            value="MAN"
            required>
    <label for="man"> М</label>
    <input  id="woman"
            type="radio"
            th:field="*{gender}"
            name="gender"
            value="WOMAN"
            style="margin-left: 24px;">
    <label for="woman"> Ж</label>
  </div>
  <br> -->


<!--<div th:if="${param.error}">-->
<!--  Invalid username and password.-->
<!--</div>-->
<!--<div th:if="${param.logout}">-->
<!--  You have been logged out.-->
<!--</div>-->