package com.example.cookingbook.controller;
import com.example.cookingbook.database.DatabaseManager;
import com.example.cookingbook.database.DishDAO;
import com.example.cookingbook.database.DishDAOImpl;
import com.example.cookingbook.model.Dish;
import com.example.cookingbook.neuralNetwork.TextDivider;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.List;

/**
 * Контроллер для работы с окном деталей блюда.
 * Управляет полями ввода и кнопками сохранения изменений.
 *
 * @autor Комов Степан
 * @version 1.0
 * @since 2024-05-20
 */
public class DishDetailsController {

    @FXML
    private TextArea nameTextArea;

    @FXML
    private TextArea cookingTimeTextArea;

    @FXML
    private TextArea ingredientsTextArea;

    @FXML
    private TextArea cookingProcessTextArea;

    @FXML
    private CheckBox favoriteCheckBox;

    private Dish selectedDish;
    private DishDAO dishDAO;

    /**
     * Метод инициализации, вызываемый после загрузки FXML.
     * Инициализирует DAO для работы с базой данных.
     */
    @FXML
    private void initialize() {
        this.dishDAO = new DishDAOImpl(new DatabaseManager());
    }

    /**
     * Устанавливает выбранное блюдо и заполняет поля его деталями.
     *
     * @param dish Выбранное блюдо.
     */
    public void setSelectedDish(Dish dish) {
        this.selectedDish = dish;
        populateFields();
    }

    /**
     * Заполняет поля деталями выбранного блюда.
     */
    private void populateFields() {
        if (selectedDish != null) {
            nameTextArea.setText(selectedDish.getName());
            cookingTimeTextArea.setText(selectedDish.getCookingTime());
            ingredientsTextArea.setText(selectedDish.getIngredients());
            divideCookingProcess(selectedDish.getCookingProcess());
            favoriteCheckBox.setSelected(selectedDish.isFavorite());
        }
    }

    /**
     * Разделяет процесс приготовления на отдельные шаги и отображает в текстовом поле.
     *
     * @param cookingProcess Полный текст процесса приготовления.
     */
    private void divideCookingProcess(String cookingProcess) {
        List<String> steps = TextDivider.splitTextIntoSteps(cookingProcess);
        StringBuilder dividedText = new StringBuilder();

        for (int i = 0; i < steps.size(); i++) {
            String[] sentences = steps.get(i).split("\\.\\s*");
            dividedText.append("Шаг ").append(i + 1).append(":\n");

            for (String sentence : sentences) {
                dividedText.append(sentence).append(".\n");
            }

            dividedText.append("\n");
        }

        cookingProcessTextArea.setText(dividedText.toString());
    }

    /**
     * Сохраняет внесенные изменения в блюде и обновляет его в базе данных.
     */
    @FXML
    private void saveChanges() {
        if (selectedDish != null) {
            selectedDish.setName(nameTextArea.getText());
            selectedDish.setCookingTime(cookingTimeTextArea.getText());
            selectedDish.setIngredients(ingredientsTextArea.getText());
            selectedDish.setCookingProcess(saveChangesWithoutSteps());
            selectedDish.setFavorite(favoriteCheckBox.isSelected());

            dishDAO.updateDish(selectedDish);

            closeWindow();
        }
    }

    /**
     * Закрывает окно деталей блюда.
     */
    @FXML
    private void closeWindow() {
        nameTextArea.getScene().getWindow().hide();
    }

    /**
     * Удаляет префиксы "Шаг N:" из текста процесса приготовления перед сохранением.
     *
     * @return Текст процесса приготовления без префиксов "Шаг N:".
     */
    private String saveChangesWithoutSteps() {
        String fullText = cookingProcessTextArea.getText();
        return fullText.replaceAll("Шаг \\d+:\\n", "");
    }
}