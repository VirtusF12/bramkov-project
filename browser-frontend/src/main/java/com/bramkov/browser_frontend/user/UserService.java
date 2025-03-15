package com.bramkov.browser_frontend.user;

import com.bramkov.browser_frontend.request.FixJsonValidation;
import com.bramkov.browser_frontend.request.HttpRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.client.RestClient;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    public UserService(DiscoveryClient discoveryClient,
                       RestClient.Builder restClientBuilder
    ) {
        this.discoveryClient = discoveryClient;
        this.restClient = restClientBuilder.build();
    }

    public void updateUser(User user, String namePage) {

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String username = auth.getName();
        ServiceInstance serviceInstance = discoveryClient
                .getInstances("user-microservice")
                .get(0);
        String requestBody = """ 
                """;

        switch (namePage) {
            case "description":
                requestBody = """
                        {
                            "description": "%s"
                        }
                        """
                        .formatted(FixJsonValidation.escapeJsonString(user.getDescription()));
                break;
            case "dob":
                requestBody = """
                        {
                            "dob": "%s"
                        }
                        """
                        .formatted(FixJsonValidation.escapeJsonString(user.getDob().toString()));
                break;
            case "name":
                requestBody = """
                        {
                            "name": "%s"
                        }
                        """
                        .formatted(FixJsonValidation.escapeJsonString(user.getName()));
                break;
            case "gender":
                requestBody = """
                        {
                            "gender": "%s"
                        }
                        """
                        .formatted(FixJsonValidation.escapeJsonString(user.getGender().toString()));
                break;
        }

        log.info("""
                requestBody = {}
                """, requestBody);

        if (FixJsonValidation.isValidJson(requestBody)) {
            try {
                HttpRequest.post(serviceInstance.getUri() +
                        "/api/v1/users/update/" + username, requestBody);
                log.info("""
                        The request has been sent
                        """);
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        } else {
            log.error("""
                       Json is not valid = {}
                       """, requestBody);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = findByUsernameFromMicroservice(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s not found.'", user.getUsername()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private User fetchUser(final String jsonUser) {

        try {

            Object obj = new JSONParser().parse(jsonUser);
            JSONObject jo = (JSONObject) obj;
            String username = (String) jo.get("username");
            String password = (String) jo.get("password");
            log.info("""
                    username = ({}),
                    password = ({})
                    """, username, password);

            Set<Role> roles = new HashSet<>();
            JSONArray ja = (JSONArray) jo.get("roles"); // nameRoles = [{"name":"USER","id":1}]
            for (Object o : ja) {
                JSONObject jRole = (JSONObject) o;
                String nameRole = (String) jRole.get("name");
                roles.add(new Role(nameRole));
            }

            return new User(username, password, roles);

        } catch (ParseException parseException) {
            return null;
        }
    }

    /**
        @from user-microservice
        @return User
    */
    private User findByUsernameFromMicroservice(String username) {

        ServiceInstance serviceInstance = discoveryClient
                .getInstances("user-microservice")
                .get(0);

        String jsonUser = restClient.get()
                .uri(serviceInstance.getUri() + "/api/v1/users/username/"+username)
                .retrieve()
                .body(String.class);

        return fetchUser(jsonUser);
    }


    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {

        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}