package com.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.mail.Session;
import javax.persistence.Query;
import javax.persistence.Table;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Table(name = "users", schema = "IP")
@NoArgsConstructor
public class Login implements LoginService {

    @Override
    public boolean login(String identifier, String password) {

        DbConnection connection = new DbConnection();
        int exists = 0;

        try {
            PreparedStatement statement = connection.getConnection()
                    .prepareStatement("Select count(*) as result from users where email=? or username=?");


            statement.setString(1, identifier);
            statement.setString(2, identifier);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                exists = resultSet.getInt("result");
            }

            if (exists == 1) {
                PreparedStatement checkPassword = connection.getConnection()
                        .prepareStatement("Select hash_password as password from users where email=? or username=?");

                checkPassword.setString(1, identifier);
                checkPassword.setString(2, identifier);

                ResultSet resultPassword = checkPassword.executeQuery();

                while (resultPassword.next()) {

                    if (BCrypt.checkpw(password, resultPassword.getString("password"))) {
                        return true;
//                        System.out.println("logged in");
                    } else {
                        return false;

//                        System.out.println("username and password do not match");
                    }
                }
            } else {
                return false;
//                System.out.println("nonexistent account");
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return false;
    }
}
