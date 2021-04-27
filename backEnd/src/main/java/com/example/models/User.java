package com.example.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "users", schema = "public")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "users_registration_id_seq")
    private Integer registrationId;

    @Column(name = "username")
    private String username;

    @Column(name = "hash_password")
    private String hashPassword;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "is_admin")
    private int isAdmin;

    @Column(name = "is_enabled")
    private boolean isEnabled = false;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinColumn(name = "id_institution", referencedColumnName = "id_institution", table = "institution_admins")
    @JoinTable(name = "institution_admins", joinColumns = {@JoinColumn(name = "id_user")},
            inverseJoinColumns = {@JoinColumn(name = "id_institution")})
    List<Institution> institutions;

    public User(String username, String hashPassword, String name, String surname, String email) {
        this.username = username;
        this.hashPassword = hashPassword;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("0");
        return Collections.singletonList(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return hashPassword;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
