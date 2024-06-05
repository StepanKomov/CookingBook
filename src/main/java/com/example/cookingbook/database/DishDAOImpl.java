package com.example.cookingbook.database;

import com.example.cookingbook.model.Dish;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Реализация интерфейса DishDAO для работы с базой данных блюд.
 * @autor Комов Степан
 * @version 1.0
 * @since 2024-05-20
 */
public class DishDAOImpl implements DishDAO{
    private final DatabaseManager databaseManager;
    /**
     * Конструктор для инициализации объекта DishDAOImpl.
     *
     * @param databaseManager Менеджер базы данных.
     */
    public DishDAOImpl(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    /**
     * Получает список всех блюд из базы данных.
     *
     * @return Список всех блюд.
     */
    @Override
    public List<Dish> getAllDishes() {
        List<Dish> dishes = new ArrayList<>();
        try {
            String query = "SELECT id, name, cooking_time, ingredients, cooking_process, favorite FROM dishes";
            PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String cookingTime = resultSet.getString("cooking_time");
                String ingredients = resultSet.getString("ingredients");
                String cookingProcess = resultSet.getString("cooking_process");
                boolean favorite = resultSet.getBoolean("favorite");
                Dish dish = new Dish(name, cookingTime, ingredients, cookingProcess, favorite);
                dish.setId(id);
                dishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }
    /**
     * Добавляет новое блюдо в базу данных.
     *
     * @param dish Новое блюдо для добавления.
     */


    @Override
    public void addDish(Dish dish) {
        String query = "INSERT INTO dishes (name, cooking_time, ingredients, cooking_process, favorite) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, dish.getName());
            preparedStatement.setString(2, dish.getCookingTime());
            preparedStatement.setString(3, dish.getIngredients());
            preparedStatement.setString(4, dish.getCookingProcess());
            preparedStatement.setBoolean(5, dish.isFavorite());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                dish.setId(generatedId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Обновляет информацию о блюде в базе данных.
     *
     * @param dish Объект блюда с обновленными данными.
     */
    @Override
    public void updateDish(Dish dish) {
        String query = "UPDATE dishes SET name = ?, cooking_time = ?, ingredients = ?, cooking_process = ?, favorite = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, dish.getName());
            preparedStatement.setString(2, dish.getCookingTime());
            preparedStatement.setString(3, dish.getIngredients());
            preparedStatement.setString(4, dish.getCookingProcess());
            preparedStatement.setBoolean(5, dish.isFavorite());
            preparedStatement.setInt(6, dish.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Удаляет блюдо из базы данных по его ID.
     *
     * @param id ID блюда для удаления.
     */
    @Override
    public void deleteDish(int id) {
        String query = "DELETE FROM dishes WHERE id = ?";
        try (PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Ищет блюда в базе данных по заданному тексту.
     *
     * @param searchText Текст для поиска в названиях блюд.
     * @return Список найденных блюд.
     */
    @Override
    public List<Dish> searchDishes(String searchText) {
        List<Dish> foundDishes = new ArrayList<>();
        try {
            String query = "SELECT id, name, cooking_time, ingredients, cooking_process, favorite " +
                    "FROM dishes " +
                    "WHERE name LIKE ?";
            PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, "%" + searchText + "%"); // Используем параметр для поиска совпадений внутри строки
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String cookingTime = resultSet.getString("cooking_time");
                String ingredients = resultSet.getString("ingredients");
                String cookingProcess = resultSet.getString("cooking_process");
                boolean favorite = resultSet.getBoolean("favorite");
                Dish dish = new Dish(name, cookingTime, ingredients, cookingProcess, favorite);
                dish.setId(id);
                foundDishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundDishes;
    }

    /**
     * Получает список избранных блюд из базы данных.
     *
     * @return Список избранных блюд.
     */
    @Override
    public List<Dish> getFavoriteDishes() {
        List<Dish> favoriteDishes = new ArrayList<>();
        try {
            String query = "SELECT id, name, cooking_time, ingredients, cooking_process, favorite FROM dishes WHERE favorite = ?";
            PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(query);
            preparedStatement.setBoolean(1, true);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String cookingTime = resultSet.getString("cooking_time");
                String ingredients = resultSet.getString("ingredients");
                String cookingProcess = resultSet.getString("cooking_process");
                boolean favorite = resultSet.getBoolean("favorite");
                Dish dish = new Dish(name, cookingTime, ingredients, cookingProcess, favorite);
                dish.setId(id);
                favoriteDishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favoriteDishes;
    }
    /**
     * Получает список недавно добавленных блюд из базы данных.
     *
     * @return Список недавно добавленных блюд.
     */
    @Override
    public List<Dish> getRecentlyAddedDishes() {
        List<Dish> recentlyAddedDishes = new ArrayList<>();
        try {
            String query = "SELECT id, name, cooking_time, ingredients, cooking_process, favorite FROM dishes ORDER BY id DESC LIMIT 10";
            PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String cookingTime = resultSet.getString("cooking_time");
                String ingredients = resultSet.getString("ingredients");
                String cookingProcess = resultSet.getString("cooking_process");
                boolean favorite = resultSet.getBoolean("favorite");
                Dish dish = new Dish(name, cookingTime, ingredients, cookingProcess, favorite);
                dish.setId(id);
                recentlyAddedDishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recentlyAddedDishes;
    }
    /**
     * Получает список блюд из базы данных, отсортированных по алфавиту.
     *
     * @return Список блюд, отсортированных по алфавиту.
     */
    @Override
    public List<Dish> getAlphabeticallySortedDishes() {
        List<Dish> alphabeticallySortedDishes = new ArrayList<>();
        try {
            String query = "SELECT id, name, cooking_time, ingredients, cooking_process, favorite FROM dishes ORDER BY name";
            PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String cookingTime = resultSet.getString("cooking_time");
                String ingredients = resultSet.getString("ingredients");
                String cookingProcess = resultSet.getString("cooking_process");
                boolean favorite = resultSet.getBoolean("favorite");
                Dish dish = new Dish(name, cookingTime, ingredients, cookingProcess, favorite);
                dish.setId(id);
                alphabeticallySortedDishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alphabeticallySortedDishes;
    }
}