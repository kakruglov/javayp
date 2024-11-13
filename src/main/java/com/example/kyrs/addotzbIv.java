package com.example.kyrs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class addotzbIv {
    @FXML
    private TextArea AreaFeedBack;
    @FXML
    private Button addFeedBack;
    @FXML
    private Button back;
    // Поле для хранения идентификатора книги
    private int bookId;
    // Объект для работы с базой данных
    DB db = new DB();
    private boolean isAdmin;
    // Метод для установки идентификатора книги
    public void setBookId(int id_products) throws SQLException, ClassNotFoundException {
        this.bookId = id_products;
    }
    // Метод инициализации контроллера
    @FXML
    void initialize() {
        db = new DB();
        // Обработчик нажатия кнопки "Добавить отзыв"
        addFeedBack.setOnAction(event -> {
            addFeedBack();
        });
        // Обработчик нажатия кнопки "Назад"
        back.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader;
                // Установите значение isAdmin, используя метод getUser
                int role = db.getUser(Authoriz.currentLogin, Authoriz.currentPassword);
                if (role == 1) {
                    fxmlLoader = new FXMLLoader(getClass().getResource("adminknigi.fxml"));
                } else  {
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
    // Метод для добавления отзыва
    @FXML
    void addFeedBack() {
        String feedback = AreaFeedBack.getText();
        try {
            // Проверка существования id_products в таблице products
            if (db.productExists(bookId)) {
                db.addFeedBack(feedback, bookId);

                // Отображение сообщения об успешном добавлении отзыва
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Успех");
                alert.setHeaderText(null);
                alert.setContentText("Ваш отзыв успешно добавлен!");
                alert.showAndWait();
                FXMLLoader fxmlLoader;
                // Установите значение isAdmin, используя метод getUser
                int role = db.getUser(Authoriz.currentLogin, Authoriz.currentPassword);
                if (role == 1) {
                    fxmlLoader = new FXMLLoader(getClass().getResource("adminknigi.fxml"));
                } else{
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

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}