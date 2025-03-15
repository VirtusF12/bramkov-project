package com.bramkov.browser_frontend.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class User {

    private Long id;
    private String username;
    private String password;
    private String passwordConfirm;
    private Set<Role> roles = new HashSet<Role>();

    private Gender gender; // +
    private String name; // +
    private String surname;
    private String description; // +
    private String email;
    private String phone;
    private LocalDate dob; // +
    private LocalDateTime lastVisit;

    public User() {}

    public User(String username,
                String password,
                Set<Role> roles) {

        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(String username,
                String password,
                String passwordConfirm,
                Set<Role> roles,
                Gender gender,
                String name,
                String surname,
                String description,
                String email,
                String phone,
                LocalDate dob,
                LocalDateTime lastVisit) {

        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.roles = roles;
        this.gender = gender;
        this.name = name;
        this.surname = surname;
        this.description = description;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.lastVisit = lastVisit;
    }

    public User(Long id,
                String username,
                String password,
                String passwordConfirm,
                Set<Role> roles,
                Gender gender,
                String name,
                String surname,
                String description,
                String email,
                String phone,
                LocalDate dob,
                LocalDateTime lastVisit) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.roles = roles;
        this.gender = gender;
        this.name = name;
        this.surname = surname;
        this.description = description;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.lastVisit = lastVisit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public LocalDateTime getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(LocalDateTime lastVisit) {
        this.lastVisit = lastVisit;
    }
}