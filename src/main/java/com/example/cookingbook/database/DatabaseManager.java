package com.example.cookingbook.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Менеджер базы данных для управления подключением к SQLite базе данных.
 * Предоставляет методы для установления и закрытия соединения с базой данных.
 *
 * @autor Комов Степан
 * @version 1.0
 * @since 2024-05-20
 */
public class DatabaseManager {

    private static final String DATABASE_URL = "jdbc:sqlite:C:/Program Files/SQLiteStudio/storage/cookingbook";
    private Connection connection;

    /**
     * Конструктор класса. Устанавливает соединение с базой данных.
     */
    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    /**
     * Получает текущее соединение с базой данных.
     *
     * @return Соединение с базой данных.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Закрывает соединение с базой данных.
     */
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}