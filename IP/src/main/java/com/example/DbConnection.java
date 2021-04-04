package com.example;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DbConnection {

    private Connection connection = null;

    public DbConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "gabriela");
            connection.setAutoCommit(false);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }
}
