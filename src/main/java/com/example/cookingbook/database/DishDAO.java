package com.example.cookingbook.database;

import com.example.cookingbook.model.Dish;
import java.util.List;

/**
 * Интерфейс, определяющий методы для работы с базой данных блюд.
 *
 * @autor Комов Степан
 * @version 1.0
 * @since 2024-05-20
 */
public interface DishDAO {

    /**
     * Получает список всех блюд из базы данных.
     *
     * @return Список всех блюд.
     */
    List<Dish> getAllDishes();

    /**
     * Добавляет новое блюдо в базу данных.
     *
     * @param dish Новое блюдо.
     */
    void addDish(Dish dish);

    /**
     * Обновляет информацию о блюде в базе данных.
     *
     * @param dish Блюдо для обновления.
     */
    void updateDish(Dish dish);

    /**
     * Удаляет блюдо из базы данных по его идентификатору.
     *
     * @param id Идентификатор удаляемого блюда.
     */
    void deleteDish(int id);

    /**
     * Выполняет поиск блюд по заданному текстовому запросу.
     *
     * @param searchText Текст запроса для поиска.
     * @return Список найденных блюд.
     */
    List<Dish> searchDishes(String searchText);

    /**
     * Получает список избранных блюд из базы данных.
     *
     * @return Список избранных блюд.
     */
    List<Dish> getFavoriteDishes();

    /**
     * Получает список недавно добавленных блюд из базы данных.
     *
     * @return Список недавно добавленных блюд.
     */
    List<Dish> getRecentlyAddedDishes();

    /**
     * Получает список блюд, отсортированных по алфавиту.
     *
     * @return Список блюд, отсортированных по алфавиту.
     */
    List<Dish> getAlphabeticallySortedDishes();
}