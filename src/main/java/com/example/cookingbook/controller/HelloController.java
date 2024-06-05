package com.example.cookingbook.controller;
import com.example.cookingbook.database.DatabaseManager;
import com.example.cookingbook.database.DishDAO;
import com.example.cookingbook.database.DishDAOImpl;
import com.example.cookingbook.model.Dish;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Контроллер для работы с главной сценой приложения.
 * Управляет таблицей блюд, полем поиска и выпадающим списком сортировки.
 *
 * @autor Комов Степан
 * @version 1.0
 * @since 2024-05-20
 */
public class HelloController {

    @FXML
    private TableView<Dish> dishesTable;

    @FXML
    private TableColumn<Dish, String> nameColumn, timeColumn, ingredientsColumn;

    @FXML
    private TableColumn<Dish, Boolean> favoriteColumn;

    @FXML
    private TextField searchField;

    @FXML
    private ChoiceBox<String> sortChoiceBox;

    private final DishDAO dishDAO;

    /**
     * Конструктор класса HelloController.
     * Инициализирует DAO для работы с базой данных.
     */
    public HelloController() {
        this.dishDAO = new DishDAOImpl(new DatabaseManager());
    }

    /**
     * Метод инициализации, вызываемый после загрузки FXML.
     * Настраивает колонки таблицы, заполняет данные и добавляет обработчики событий.
     */
    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("cookingTime"));
        ingredientsColumn.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
        favoriteColumn.setCellValueFactory(new PropertyValueFactory<>("favorite"));

        List<Dish> allDishes = dishDAO.getAllDishes();
        dishesTable.getItems().addAll(allDishes);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchDishes(newValue.trim());
        });

        sortChoiceBox.getItems().addAll("По умолчанию", "Понравившиеся", "Последние добавленные", "По алфавиту");
        sortChoiceBox.setValue("По умолчанию");

        sortChoiceBox.setOnAction(event -> {
            String selectedSortType = sortChoiceBox.getValue();
            List<Dish> sortedDishes = null;
            switch (selectedSortType) {
                case "По умолчанию":
                    sortedDishes = dishDAO.getAllDishes();
                    break;
                case "Понравившиеся":
                    sortedDishes = dishDAO.getFavoriteDishes();
                    break;
                case "Последние добавленные":
                    sortedDishes = dishDAO.getRecentlyAddedDishes();
                    break;
                case "По алфавиту":
                    sortedDishes = dishDAO.getAlphabeticallySortedDishes();
                    break;
            }

            if (sortedDishes != null) {
                dishesTable.getItems().clear();
                dishesTable.getItems().addAll(sortedDishes);
            }
        });
    }

    /**
     * Обрабатывает двойной клик по строке таблицы.
     * Открывает окно с деталями выбранного блюда.
     *
     * @param event Событие клика мыши.
     */
    @FXML
    private void handleTableClick(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            Dish selectedDish = dishesTable.getSelectionModel().getSelectedItem();
            openDishDetails(selectedDish);
        }
    }

    /**
     * Обновляет таблицу блюд.
     * Загружает все блюда из базы данных и отображает их в таблице.
     */
    @FXML
    private void refreshTable() {
        dishesTable.getItems().clear();
        List<Dish> allDishes = dishDAO.getAllDishes();
        dishesTable.getItems().addAll(allDishes);
    }

    /**
     * Удаляет выбранное блюдо из таблицы и базы данных.
     */
    @FXML
    private void deleteSelectedDish() {
        Dish selectedDish = dishesTable.getSelectionModel().getSelectedItem();
        if (selectedDish != null) {
            dishDAO.deleteDish(selectedDish.getId());
            dishesTable.getItems().remove(selectedDish);
        }
    }

    /**
     * Открывает окно с деталями выбранного блюда.
     *
     * @param dish Блюдо, детали которого нужно отобразить.
     */
    private void openDishDetails(Dish dish) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cookingbook/DishDetails.fxml"));
            Parent root = loader.load();
            DishDetailsController controller = loader.getController();
            controller.setSelectedDish(dish);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Открывает окно для добавления нового блюда.
     */
    @FXML
    private void openAddDishWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cookingbook/addDish.fxml"));
            Parent root = loader.load();
            AddDishController controller = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Выполняет поиск блюд по введенному тексту.
     *
     * @param searchText Текст для поиска.
     */
    private void searchDishes(String searchText) {
        dishesTable.getItems().clear();

        if (searchText.isEmpty()) {
            List<Dish> allDishes = dishDAO.getAllDishes();
            dishesTable.getItems().addAll(allDishes);
            return;
        }

        List<Dish> foundDishes = dishDAO.searchDishes(searchText);
        dishesTable.getItems().addAll(foundDishes);
    }
}