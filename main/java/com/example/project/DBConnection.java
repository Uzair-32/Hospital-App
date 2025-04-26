package com.example.project;
import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String USER = "root";
    private static final String PASSWORD = "$trongholD1";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}