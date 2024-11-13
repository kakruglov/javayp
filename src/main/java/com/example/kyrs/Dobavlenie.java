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
import java.sql.SQLException;

public class Dobavlenie {
    @FXML
    private ComboBox<Publisher> author_id;
    @FXML
    private ComboBox<Publisher> category_id;
    @FXML
    private ComboBox<Publisher> izdatelstvo_id;
    @FXML
    private ComboBox<String> nalichies;
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField opisanie;
    @FXML
    private TextField price;
    @FXML
    private Button addProduct;
    @FXML
    private Button back;
    private int lastproductId;
    DB db = new DB();
//метод инициализации при подгрузке формы
    public void initialize() throws SQLException, ClassNotFoundException {
        // Создание списка опций для ComboBox nalichies
        ObservableList<String> options = FXCollections.observableArrayList("Есть в наличии", "Нет в наличии");
        nalichies.setItems(options);
        // Получение списка издателей, авторов и категорий из базы данных
        ObservableList<Publisher> categor = db.getCategor();
        category_id.setItems(categor);
        ObservableList<Publisher> author = db.getAuthor();
        author_id.setItems(author);
        ObservableList<Publisher> izdatel = db.getIzdatel();
        izdatelstvo_id.setItems(izdatel);

        db = new DB();
//получение последнего id продукта
        lastproductId = db.getLastProduct() + 1;
        id.setPromptText(String.valueOf(lastproductId));
//ограничение для строки цены
        price.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                price.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() > 15000) {
                price.setText(oldValue);
            }
            try {
                int number = Integer.parseInt(newValue);
                if (number < 1 || number > 15000) {
                    price.setText(oldValue);
                }
            } catch (NumberFormatException e) {
            }
        });
//метод для кнопки добавления нового товара
        addProduct.setOnAction(event -> {
            if (id.getText().isEmpty() || name.getText().isEmpty() || nalichies.getValue()==null || opisanie.getText().isEmpty() || price.getText().isEmpty() || author_id.getValue()==null || category_id.getValue()==null || izdatelstvo_id.getValue()==null)  {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Не все поля заполнены!");
                alert.showAndWait();
                return;
            }
            try {
                Integer idprod = Integer.parseInt(id.getText());
                String nal = nalichies.getValue();
                String nameStr = name.getText();
                String opisStr = opisanie.getText();
                String priceStr = price.getText();

                String authorIdStr = author_id.getValue().toString();
                int authorId = Integer.parseInt(authorIdStr.split(" - ")[0]);

                String categorIdStr = category_id.getValue().toString();
                int categorId = Integer.parseInt(categorIdStr.split(" - ")[0]);

                String izdatelIdStr = category_id.getValue().toString();
                int izdatelId = Integer.parseInt(izdatelIdStr.split(" - ")[0]);

                db.saveOrder(idprod, nameStr, priceStr, opisStr, nal, authorId, categorId, izdatelId);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Успех");
                alert.setHeaderText(null);
                alert.setContentText("Товар успешно добавлен!");
                alert.showAndWait();

                lastproductId++;
                id.setText(String.valueOf(lastproductId));
                nalichies.getSelectionModel().clearSelection();
                name.clear();
                opisanie.clear();
                price.clear();
                author_id.getSelectionModel().clearSelection();
                izdatelstvo_id.getSelectionModel().clearSelection();
                category_id.getSelectionModel().clearSelection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
//кнопка возращения
        back.setOnAction(event -> {
            try {
                // Загрузите FXML-файл
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminKnigi.fxml"));
                Parent root = loader.load();
                // Получите контроллер из FXML-файла
                AdminKnigi controller = loader.getController();
                // Создайте новую сцену с загруженным корневым элементом
                Scene scene = new Scene(root);
                // Создайте новое окно Stage
                Stage stage = new Stage();
                stage.setTitle("Просмотр списка");
                stage.getIcons().add(new Image("C:\\Users\\User\\Desktop\\kyrs\\src\\main\\resources\\com\\example\\kyrs\\checkbook.png"));
                // Установите сцену для окна Stage
                stage.setScene(scene);
                // Покажите окно
                stage.show();
                // Закройте текущую форму AdminKnigi.fxml
                Stage currentStage = (Stage) back.getScene().getWindow();
                Stage ownerStage = (Stage) currentStage.getOwner();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}









































