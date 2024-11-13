package com.example.kyrs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Redactirovanie {
    @FXML
    private Button back;
    @FXML
    private Button update;
    @FXML
    private ComboBox<Publisher> author_id;
    @FXML
    private ComboBox<Publisher> izdatelstvo_id;
    @FXML
    private ComboBox<Publisher> category_id;
    @FXML
    private ComboBox<String> nalichies;
    @FXML
    private TextField idName;
    @FXML
    private TextField idOpis;
    @FXML
    private TextField idPrice;
    DB db = new DB();
    private int bookId;
    public void setDb(DB db) {
        this.db = db;
    }
    // Метод для установки идентификатора книги
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        // Создание списка опций для ComboBox nalichies
        ObservableList<String> options = FXCollections.observableArrayList("Есть в наличии", "Нет в наличии");
        nalichies.setItems(options);
        // Получение данных из базы данных для заполнения ComboBox
        ObservableList<Publisher> authors = db.getAuthor();
        ObservableList<Publisher> izdatelstva = db.getIzdatel();
        ObservableList<Publisher> categories = db.getCategor();
        // Обработка действий нажатия кнопки "back"
        back.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminKnigi.fxml"));
                Parent root = loader.load();
                // Получите контроллер из FXML-файла
                AdminKnigi controller = loader.getController();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Просмотр списка");
                stage.getIcons().add(new Image("C:\\Users\\User\\Desktop\\kyrs\\src\\main\\resources\\com\\example\\kyrs\\checkbook.png"));
                stage.setScene(scene);
                stage.show();
                // Закройте текущую форму AdminKnigi.fxml
                Stage currentStage = (Stage) back.getScene().getWindow();
                Stage ownerStage = (Stage) currentStage.getOwner();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
// Обработка действий нажатия кнопки "update"
        update.setOnAction(event -> {
            if (idName.getText().isEmpty() || idOpis.getText().isEmpty() || idPrice.getText().isEmpty())  {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Не все поля заполнены!");
                alert.showAndWait();
                return;
            }
            try {
                String sql = "UPDATE products SET name = ?, opisanie = ?, price = ?, nalichie = ?,author_id = ?, category_id = ?, izdatelstvo_id = ? WHERE id = ?";
                PreparedStatement statement = db.getDbConnection().prepareStatement(sql);
                statement.setString(1, idName.getText());
                statement.setString(2, idOpis.getText());
                statement.setString(3, idPrice.getText());
                statement.setString(4, nalichies.getValue());
                statement.setInt(5, author_id.getValue().getId());
                statement.setInt(6, category_id.getValue().getId());
                statement.setInt(7, izdatelstvo_id.getValue().getId());
                statement.setInt(8, bookId);
                statement.executeUpdate();
                //Сообщение о удачном изменении
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Успех");
                alert.setHeaderText(null);
                alert.setContentText("Данные успешно изменены!");
                alert.showAndWait();
                // Загрузить обновленные данные книги
                loadBookData();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminKnigi.fxml"));
                Parent root = loader.load();
                // Получите контроллер из FXML-файла
                AdminKnigi controller = loader.getController();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Просмотр списка");
                stage.getIcons().add(new Image("C:\\Users\\User\\Desktop\\kyrs\\src\\main\\resources\\com\\example\\kyrs\\checkbook.png"));
                stage.setScene(scene);
                stage.show();
                // Закройте текущую форму AdminKnigi.fxml
                Stage currentStage = (Stage) update.getScene().getWindow();
                currentStage.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Установка элементов для ComboBox
        author_id.setItems(authors);
        izdatelstvo_id.setItems(izdatelstva);
        category_id.setItems(categories);
    }
    // Метод для загрузки данных о книге из базы данных
    public void loadBookData() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM products WHERE id = ?";
        PreparedStatement statement = db.getDbConnection().prepareStatement(sql);
        statement.setInt(1, bookId);
        ResultSet res = statement.executeQuery();

        // Заполнение полей формы данными из базы данных
        if (res.next()) {
            idName.setText(res.getString("name"));
            idOpis.setText(res.getString("opisanie"));
            idPrice.setText(res.getString("price"));
            nalichies.setValue(res.getString("nalichie"));

            // Заполнение ComboBox выбранными значениями
            author_id.getSelectionModel().select(Integer.parseInt(res.getString("author_id")));
            category_id.getSelectionModel().select(Integer.parseInt(res.getString("category_id")));
            izdatelstvo_id.getSelectionModel().select(Integer.parseInt(res.getString("izdatelstvo_id")));
        }
    }
}

