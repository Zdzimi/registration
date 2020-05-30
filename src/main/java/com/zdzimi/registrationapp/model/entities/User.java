package com.zdzimi.registrationapp.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zdzimi.registrationapp.security.Role;
import org.springframework.hateoas.EntityModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class User extends EntityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @JsonIgnore
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Visit> visits = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    private Set<Institution> institutions = new HashSet<>();

    public User() {
    }

    public User(String username, String name, String surname, String email, String password, Role role) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }

    public Set<Institution> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(Set<Institution> institutions) {
        this.institutions = institutions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUserId() == user.getUserId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }
}
