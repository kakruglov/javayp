package com.example.kyrs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class StrStranic extends ListCell<StranicData> {
    @FXML
    private AnchorPane idContainer;
    @FXML
    private ImageView idImg;
    @FXML
    private HBox idStr;
    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(StranicData stranicData, boolean empty) {// Метод для обновления элемента ячейки списка
        super.updateItem(stranicData, empty);
        if (empty || stranicData == null) { // Проверка на пустоту данных и наличие объекта StranicData
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("listStranic.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } // Загрузка изображения
            File file = new File(stranicData.getPhoto());
            try {
                String urlImage = file.toURI().toURL().toString();
                Image image = new Image(urlImage);
                idImg.setImage(image);
            } catch (MalformedURLException ignored) {
            } // Установка графики для элемента списка
            setText(null);
            setGraphic(idContainer);
        }
    }
}