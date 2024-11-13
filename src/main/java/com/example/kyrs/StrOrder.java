package com.example.kyrs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class StrOrder extends ListCell<OrderData>{
    @FXML
    private AnchorPane idContainer;
    @FXML
    private Label idFio;
    @FXML
    private ImageView idImg;
    @FXML
    private TextField idKolvo;
    @FXML
    private Label idOpis;
    @FXML
    private Label idPrice;
    @FXML
    private HBox idStr;
    private FXMLLoader mLLoader;

    // Метод инициализации
    @FXML
    private void initialize() {
        idKolvo.setText(String.valueOf(1)); // Установка значения по умолчанию - 1
        idKolvo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { // Проверка, что в поле введены только цифры
                idKolvo.setText(newValue.replaceAll("[^\\d]", "")); // Удаление недопустимых символов из поля
            }

            if (newValue.length() > 20) { // Ограничение длины поля - 20 символов
                idKolvo.setText(oldValue);
            }

            // Проверка, что введенное число находится в диапазоне от 1 до 20
            try {
                int number = Integer.parseInt(newValue);
                if (number < 1 || number > 20) {
                    idKolvo.setText(oldValue);
                }
            } catch (NumberFormatException e) {
            }
        });
    }

    // Метод для обновления элемента ячейки списка
    @Override
    protected void updateItem(OrderData student, boolean empty) {
        super.updateItem(student, empty);
        if (empty || student == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("listOrder.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            idFio.setText(student.getName()); // Устанавливаем Название товара
            idOpis.setText(student.getOpisanie()); // Устанавливаем описание
            idPrice.setText(student.getPrice()); // Устанавливаем цену
            String photopath = student.getPhoto(); // Получаем путь к изображению

            if (photopath == null) {
                Image defaultImage = new Image("C:\\Users\\User\\Desktop\\kyrs\\zaglysh\\picture.png");
                idImg.setImage(defaultImage);
            } else {
                //Установка изображения
                File file = new File(student.getPhoto());
                try {
                    String urlImage = file.toURI().toURL().toString();
                    Image image = new Image(urlImage);
                    idImg.setImage(image);
                } catch (MalformedURLException ignored) {
                }
            } // Установка графики для элемента списка
            setText(null);
            setGraphic(idContainer);
        }
    }

    // Метод для получения количества
    public int getQuantity() {
        if (idKolvo != null) {
            return Integer.parseInt(idKolvo.getText());
        } else {
            return 0;
        }
    }

}