package com.example;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "users", schema = "IP")
@Transactional
public class Registration implements RegistrationService {

    @Id
    private String registrationId;

    @Override
    public void createUser(String email, String username, String name, String surname, String password) {

        PreparedStatement statement = null;
        String insertUser = "Insert into users (registration_id, email, username, name, surname, hash_password)" +
                " values ((SELECT COALESCE(MAX(registration_id)+1,1) FROM users),?, ?, ?, ?, ?)";

        try {
            DbConnection connection = new DbConnection();
            statement = connection.getConnection().prepareStatement(insertUser);

            User user = new User(username, BCrypt.hashpw(password, BCrypt.gensalt(10)), name, surname, email);

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setString(5, user.getHashPassword());

            statement.executeUpdate();

            connection.getConnection().commit();
            connection.getConnection().close();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

}
