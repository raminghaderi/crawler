package com.datenbanken.webcrawler.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ramin
 */
public class BaseRepository {

    private static final String URL = "jdbc:mysql://localhost:3306/dwt?userUnicode=yes&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    protected static Connection getConnection() throws SQLException {

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        connection.setAutoCommit(false);
        return connection;
    }

}
