package com.example.kyrs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class StrProd extends ListCell<ProductData> {
    @FXML
    private AnchorPane idContainer;
    @FXML
    private Label idName;
    @FXML
    private ImageView idImg;
    @FXML
    private Label idNalic;
    @FXML
    private Label idOpis;
    @FXML
    private Label idPrice;
    @FXML
    private HBox idStr;
    @FXML
    private Button checkStr;

    private FXMLLoader mLLoader;

    @FXML
    private void initialize() {
    }

    @Override
    protected void updateItem(ProductData student, boolean empty) {
        super.updateItem(student, empty);
        // Обновление содержимого ячейки в списке
        if (empty || student == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Загрузка FXML файла и обновление полей интерфейса
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("listProduct.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Обновление полей интерфейса с использованием данных из объекта student
            idName.setText(student.getName());
            idOpis.setText(student.getOpisanie());
            idPrice.setText(student.getPrice());
            idNalic.setText(student.getNalichie());
            // Обработка случая, когда изображение продукта отсутствует
            String photopath = student.getPhoto();
            if (photopath == null) {
                Image defaultImage = new Image("C:\\Users\\User\\Desktop\\kyrs\\zaglysh\\picture.png");
                idImg.setImage(defaultImage);
            } else {
                File file = new File(student.getPhoto());
                try {
                    String urlImage = file.toURI().toURL().toString();
                    Image image = new Image(urlImage);
                    idImg.setImage(image);
                } catch (MalformedURLException ignored) {
                }
            }
            // Изменение цвета фона в зависимости от наличия продукта
            if (student.getNalichie().equals("Нет в наличии")) {
                idContainer.setStyle("-fx-background-color: gray");
            } else {
                idContainer.setStyle("");
            }
            setText(null);
            setGraphic(idContainer);
        }
    }
}
