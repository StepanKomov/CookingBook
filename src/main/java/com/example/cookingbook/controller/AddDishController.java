package com.example.cookingbook.controller;
import com.example.cookingbook.database.DatabaseManager;
import com.example.cookingbook.database.DishDAO;
import com.example.cookingbook.database.DishDAOImpl;
import com.example.cookingbook.model.Dish;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Контроллер для добавления нового блюда.
 * Управляет полями ввода и кнопкой сохранения блюда.
 *
 * @autor Комов Степан
 * @version 1.0
 * @since 2024-05-20
 */
public class AddDishController {

    private DishDAO dishDAO;

    @FXML
    private TextField nameField;

    @FXML
    private TextField cookingTimeField;

    @FXML
    private TextArea ingredientsArea;

    @FXML
    private TextArea cookingProcessArea;

    @FXML
    private CheckBox favoriteCheckBox;

    /**
     * Метод инициализации, вызываемый после загрузки FXML.
     * Инициализирует DAO для работы с базой данных.
     */
    @FXML
    private void initialize() {
        this.dishDAO = new DishDAOImpl(new DatabaseManager());
    }

    /**
     * Сохраняет введенные данные о новом блюде.
     */
    @FXML
    private void saveDish() {
        String name = nameField.getText();
        String cookingTime = cookingTimeField.getText();
        String ingredients = ingredientsArea.getText();
        String cookingProcess = cookingProcessArea.getText();
        boolean favorite = favoriteCheckBox.isSelected();

        Dish newDish = new Dish(name, cookingTime, ingredients, cookingProcess, favorite);

        dishDAO.addDish(newDish);

        closeWindow();
    }

    /**
     * Закрывает окно добавления блюда.
     */
    @FXML
    private void closeWindow() {
        nameField.getScene().getWindow().hide();
    }
}