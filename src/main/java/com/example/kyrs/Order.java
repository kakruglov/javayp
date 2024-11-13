package com.example.kyrs;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Order {
    @FXML
    private ComboBox<String> PickupPoint;
    @FXML
    private Button addOrder;
    @FXML
    private Button back;
    @FXML
    private Label idPrice;
    @FXML
    private ListView<String> korzina;
    @FXML
    private ListView<OrderData> idspis;
    @FXML
    private Button delteKorzina;
    double totalPrice = 0;
    DB db = new DB();

    @FXML
    void deleteKorzina(ActionEvent event) {
        korzina.getItems().clear();
        idPrice.setText("");
        totalPrice = 0;
    }
//добавление нового заказа
    @FXML
    void addOrder(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        String selectedPickupPoint = PickupPoint.getSelectionModel().getSelectedItem();
        if (korzina.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Корзина пуста");
            alert.showAndWait();
            return;
        }
        if (PickupPoint.getValue()==null)  {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Пункт выдачи не выбран!");
            alert.showAndWait();
            return;
        }
        int pickupPointId = Integer.parseInt(selectedPickupPoint.split(" - ")[0]);
        double totalPrice = Double.parseDouble(idPrice.getText());
        ArrayList<String> products = new ArrayList<>(korzina.getItems());
        db.addOrder(pickupPointId, totalPrice, products);
        //сообщение о оформление заказа
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Новый заказ");
        alert.setHeaderText(null);
        alert.setContentText("Оформление прошло успешно!");
        alert.showAndWait();
        // Очистить все поля формы
        PickupPoint.getSelectionModel().clearSelection();
        korzina.getItems().clear();
        idPrice.setText("");
        Stage currentStage = (Stage) addOrder.getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminKnigi.fxml"));
        Parent root = loader.load();
        // Получите контроллер из FXML-файла
        AdminKnigi controller = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        ObservableList<String> pickup = db.getPickupPoint();
        PickupPoint.setItems(pickup);
        db = new DB();
        loadInfo();
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
    void loadInfo() {
        try {
            List<OrderData> list = db.getProducts();
            idspis.getItems().addAll(list);
            // Создаем переменную для хранения общей цены
            idspis.setCellFactory(stringListView -> {
                ListCell<OrderData> cell = new StrOrder();
                ContextMenu contextMenu = new ContextMenu();
                MenuItem menuItem1 = new MenuItem("Добавить");
                menuItem1.setOnAction(event -> {
                    OrderData selectedProduct = cell.getItem();
                    int quantity = ((StrOrder) cell).getQuantity(); // Получаем количество из TextField
                    korzina.getItems().add(selectedProduct.getName() + " x " + quantity); // Добавляем название продукта и количество в корзину
                    totalPrice += Double.parseDouble(selectedProduct.getPrice()) * quantity; // Увеличиваем totalPrice на цену выбранного продукта, умноженную на количество
                    idPrice.setText(String.valueOf(totalPrice)); // Устанавливаем текст idPrice равным totalPrice
                });
                contextMenu.getItems().addAll(menuItem1);
                cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                    if (isNowEmpty) {
                        cell.setContextMenu(null);
                    } else {
                        cell.setContextMenu(contextMenu);
                    }
                });
                return cell;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}