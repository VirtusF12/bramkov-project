package com.bramkov.user_microservice.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table (
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        }
)
public class User {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "username"
    )
    @NotBlank(message = "Username не должен быть пустым")
    @Size(
            min = 2,
            max = 30,
            message = "Username должен быть от 2 до 30 символов"
    )
    private String username;

    @Column(name = "password")
    @NotBlank(
            message = "Пароль не должен быть пустым"
    )
    @Size(
            min = 5,
            max = 120,
            message = "Пароль должен быть от 5 символов"
    )
    private String password;

    @Transient // поле не должно сохраняться в базе данных
    @NotBlank(
            message = "Пароль не должен быть пустым"
    )
    @Size(
            min = 5,
            message = "Пароль должен быть от 5 символов"
    )
    private String passwordConfirm;

    @Enumerated(
            value = EnumType.STRING
    )
    @Column(
            name = "gender"
    )
    private Gender gender;

    @Column(
            name = "name"
    )
    @Size(
            min = 3,
            max = 30
    )
    private String name;

    @Column(
            name = "surname"
    )
    @Size(
            min = 3,
            max = 30
    )
    private String surname;

    @Column(
            name = "description"
    )
    @Size(
            max = 500
    )
    private String description;

    @Column(
            name = "email"
    )
    @Email
    @Size(
            min = 6,
            max = 50
    )
    private String email;

    @Column(
            name = "phone"
    )
    private String phone;

    @Column(
            name = "dob"
    )
    private LocalDate dob;

    @Column(
            name = "last_visit"
    )
    private LocalDateTime lastVisit;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") )
    private Set<Role> roles = new HashSet<Role>();

    public User() {}

    public User(String username,
                String password,
                Gender gender,
                String name,
                String surname,
                String description,
                String email,
                String phone,
                LocalDate dob,
                LocalDateTime lastVisit,
                Set<Role> roles) {

        this.username = username;
        this.password = password;
        this.gender = gender;
        this.name = name;
        this.surname = surname;
        this.description = description;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.lastVisit = lastVisit;
        this.roles = roles;
    }

    /* id */
    public Long getId() {
        return id;
    }

    /* username */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /* password */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /* gender */
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /* password confirm */
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    /* name */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /* surname */
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    /* description */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /* email */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /* phone */
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /* dob */
    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    /* last visit */
    public LocalDateTime getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(LocalDateTime lastVisit) {
        this.lastVisit = lastVisit;
    }

    /* roles */
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(passwordConfirm, user.passwordConfirm) &&
                gender == user.gender &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(description, user.description) &&
                Objects.equals(email, user.email) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(dob, user.dob) &&
                Objects.equals(lastVisit, user.lastVisit) &&
                Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, passwordConfirm, gender, name, surname, description, email, phone, dob, lastVisit, roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                ", gender=" + gender +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dob=" + dob +
                ", lastVisit=" + lastVisit +
                ", roles=" + roles +
                '}';
    }
}