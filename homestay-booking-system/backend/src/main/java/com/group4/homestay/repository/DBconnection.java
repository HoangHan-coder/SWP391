package com.group4.homestay.repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnection {

    private static final String URL =
            "jdbc:sqlserver://localhost:1433;" +
                    "databaseName=homestay_booking_system;" +
                    "encrypt=true;trustServerCertificate=true";

    private static final String USER = "sa";
    private static final String PASS = "123";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("❌ Không tìm thấy SQL Server JDBC Driver", e);
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
