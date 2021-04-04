package com.example;

import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DbConnection {

    private Connection connection = null;

    public DbConnection() {
        try {
            String dbUri = System.getenv("JDBC_DATABASE_URL");
            connection = DriverManager.getConnection(dbUri);
            connection.setAutoCommit(false);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
