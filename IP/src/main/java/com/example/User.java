package com.example;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "users", schema = "IP")
public class User {
    @Id
    private int registrationId;

    @Column(name="username")
    private String username;

    @Column(name="hash_password")
    private String hashPassword;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="email")
    private String email;

    @Column(name="is_admin")
    private int isAdmin;

    public User(String username, String hashPassword, String name, String surname, String email) {
        this.username = username;
        this.hashPassword = hashPassword;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }


}
