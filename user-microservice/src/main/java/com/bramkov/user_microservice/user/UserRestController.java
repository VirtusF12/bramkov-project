package com.bramkov.user_microservice.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {

    private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

    private final UserRepository userRepository;

    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> all() {
        log.info("GET: ALL");
        return userRepository.findAll();
    }

    @GetMapping("/users/id/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping("/users/username/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/users/update/{username}")
    public User updateUser(@RequestBody User newUser, @PathVariable String username) {

        return userRepository.findByUsername(username)
                .map(user -> {

                    user.setDescription(
                            newUser.getDescription() == null
                                    ?
                            (user.getDescription() == null || user.getDescription().isEmpty() ? null : user.getDescription())
                                    :
                            newUser.getDescription()
                    );

                    user.setPasswordConfirm("sde9TAYS435k..");

                    user.setDob(
                            newUser.getDob() == null
                            ?
                            (user.getDob() == null ? null : user.getDob())
                            :
                            newUser.getDob()
                    );

                    user.setName(
                            newUser.getName() == null
                            ?
                            (user.getName() == null || user.getName().isEmpty() ? null : user.getName())
                            :
                            newUser.getName()
                    );

                    user.setGender(
                            newUser.getGender() == null
                            ?
                            (user.getGender() == null ? null : user.getGender())
                            :
                            newUser.getGender()
                    );

                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    return userRepository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) {

        log.info("DELETE: USER");
        userRepository.deleteById(id);
    }
}

