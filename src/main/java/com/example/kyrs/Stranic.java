package com.example.kyrs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Stranic {
    @FXML
    private Button back; //Кнопка назад
    @FXML
    private ListView<StranicData> idstranic;//Список для отображения данных
    DB db = new DB();// Объект для работы с базой данных
    private int bookId;// Идентификатор книги
    private Stage previousStage;// Предыдущее окно
    private boolean isAdmin;

    public void setBookId(int id_products) throws SQLException, ClassNotFoundException {
        this.bookId = id_products;
        loadInfo();
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        db = new DB();  // Инициализация объекта для работы с базой данных

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

    // Метод для загрузки информации
    void loadInfo() {
        try {
            List<StranicData> ls = db.getStranicByBookId(bookId);  // Получение данных из базы
            idstranic.getItems().addAll(ls);  // Добавление данных в список

            // Настройка отображения элементов списка
            idstranic.setCellFactory(stringListView -> {
                ListCell<StranicData> cell = new StrStranic();
                return cell;
            });
        } catch (SQLException e) {
            e.printStackTrace();  // Обработка исключения SQL
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  // Обработка исключения ClassNotFoundException
        }
    }
}