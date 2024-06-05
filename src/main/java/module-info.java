module com.example.cookingbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.cookingbook to javafx.fxml;
    exports com.example.cookingbook;
    exports com.example.cookingbook.controller;
    opens com.example.cookingbook.controller to javafx.fxml;
    opens com.example.cookingbook.model to javafx.base;
}