package com.example.cookingbook.model;
/**
 * Класс, представляющий объект блюда.
 * @autor Комов Степан
 * @version 1.0
 * @since 2024-05-20
 */
public class Dish {
    private int id; // Идентификатор блюда
    private String name; // Название блюда
    private String cookingTime; // Время приготовления
    private String ingredients; // Ингредиенты
    private String cookingProcess; // Процесс приготовления
    private boolean favorite; // Флаг, указывающий на избранность блюда
    /**
     * Конструктор класса Dish.
     *
     * @param name           Название блюда.
     * @param cookingTime    Время приготовления блюда.
     * @param ingredients    Ингредиенты для приготовления блюда.
     * @param cookingProcess Процесс приготовления блюда.
     * @param favorite       Флаг, указывающий, является ли блюдо избранным.
     */
    public Dish(String name, String cookingTime, String ingredients, String cookingProcess, boolean favorite) {
        this.name = name;
        this.cookingTime = cookingTime;
        this.ingredients = ingredients;
        this.cookingProcess = cookingProcess;
        this.favorite = favorite;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
    public String getCookingProcess() {
        return cookingProcess;
    }

    public void setCookingProcess(String cookingProcess) {
        this.cookingProcess = cookingProcess;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}