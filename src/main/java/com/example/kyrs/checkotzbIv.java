package com.example.kyrs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class checkotzbIv {
    @FXML
    private Button back;
    @FXML
    private ListView<String> listFeedBack;
    private int bookId; // Идентификатор книги
    DB db = new DB();
    private boolean isAdmin;

    // Метод для установки идентификатора книги и загрузки соответствующих отзывов
    public void setBookId(int bookId) throws SQLException, ClassNotFoundException {
        this.bookId = bookId;
        ArrayList<String> feedback = db.getFeedbackForBook(bookId);
        ObservableList<String> items = FXCollections.observableArrayList(feedback);
        // Установка элементов в ListView
        listFeedBack.setItems(items);
    }

    // Метод инициализации, вызываемый после загрузки FXML
    @FXML
    void initialize() {
        // Обработчик нажатия кнопки "Назад"
        back.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader;
                // Установите значение isAdmin, используя метод getUser
                int role = db.getUser(Authoriz.currentLogin, Authoriz.currentPassword);
                if (role == 1) {
                    fxmlLoader = new FXMLLoader(getClass().getResource("adminknigi.fxml"));
                } else {
                    fxmlLoader = new FXMLLoader(getClass().getResource("klientknigi.fxml"));
                }
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Просмотр списка");
                stage.getIcons().add(new Image("C:\\Users\\User\\Desktop\\kyrs\\src\\main\\resources\\com\\example\\kyrs\\checkbook.png"));
                stage.show();
                // Закрытие текущего окна
                Stage currentStage = (Stage) back.getScene().getWindow();
                currentStage.close();
            } catch(IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
}