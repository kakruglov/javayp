package com.example.kyrs;

import javafx.collections.ObservableList;
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
import java.util.stream.Collectors;

public class KlientKnigi {
        @FXML
        private ListView<ProductData> idspis;
        @FXML
        private ComboBox<String> idCategory;
        @FXML
        private TextField TextFieldPoisk;
        DB db = new DB();

        @FXML
        void initialize() throws SQLException, ClassNotFoundException {
            ObservableList<String> categor = db.getCategori();
            categor.addAll("Сбросить фильтр");
            idCategory.setItems(categor);
            db = new DB();
            //динамический поиск в TextField
            TextFieldPoisk.textProperty().addListener((obs, oldText, newText) -> {
                try {
                    idspis.getItems().clear();
                    List<ProductData> products = db.getProduct();
                    String searchText = newText.toLowerCase();
                    List<ProductData> filteredProducts = products.stream()
                            .filter(product -> product.getName().toLowerCase().contains(searchText) ||
                                    product.getPrice().toLowerCase().contains(searchText) ||
                                    product.getNalichie().toLowerCase().contains(searchText))
                            .collect(Collectors.toList());
                    idspis.getItems().addAll(filteredProducts);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
            //поиск с помошью выпадающего списка по жанру книги
            idCategory.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection.equals("Сбросить фильтр")) {
                    TextFieldPoisk.clear();
                    try {
                        idspis.getItems().clear();
                        List<ProductData> ls = db.getProduct();
                        idspis.getItems().addAll(ls);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    TextFieldPoisk.clear();
                    try {
                        idspis.getItems().clear();
                        // Получаем id выбранной категории
                        int selectedCategoryId = Integer.parseInt(newSelection.split(" - ")[0]);
                        List<ProductData> productsInCategory = db.getProductsByCategory(selectedCategoryId);
                        idspis.getItems().clear();
                        idspis.getItems().addAll(productsInCategory);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            // Метод для подгрузки заданий внутрь программы
            loadInfo();
        }
        //подгрузка данных
        void loadInfo() {
            try {
                List<ProductData> ls = db.getProduct();
                idspis.getItems().clear();
                idspis.getItems().addAll(ls);
                // Установка кастомного отображения ячеек списка через StrProd
                idspis.setCellFactory(stringListView -> {
                    ListCell<ProductData> cell = new StrProd();
                    ContextMenu contextMenu = new ContextMenu();
                    MenuItem menuItem1 = new MenuItem("Посмотреть отзывы");
                    MenuItem menuItem2 = new MenuItem("Добавить отзыв");
                    MenuItem menuItem3 = new MenuItem("Посмотреть страницы");
                    menuItem1.setOnAction(event -> {
                        try {
                            Stage currentStage = (Stage) menuItem1.getParentPopup().getOwnerWindow();
                            currentStage.close();
                            // Получить id выбранной книги
                            ProductData selectedBook = idspis.getSelectionModel().getSelectedItem();
                            if (selectedBook != null) {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("checkotzbIv.fxml"));
                                Parent root = loader.load();
                                checkotzbIv controller = loader.getController();
                                controller.setBookId(selectedBook.getId());
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.setTitle("Отзывы");
                                stage.getIcons().add(new Image("file:picture/checkfeedback.png"));
                                stage.show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    menuItem2.setOnAction(event -> {
                        try {
                            Stage currentStage = (Stage) menuItem2.getParentPopup().getOwnerWindow();
                            currentStage.close();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("addotzbIv.fxml"));
                            Parent root = loader.load();
                            addotzbIv controller = loader.getController();
                            // Получить id выбранной книги
                            ProductData selectedBook = idspis.getSelectionModel().getSelectedItem();
                            if (selectedBook != null) {
                                controller.setBookId(selectedBook.getId());
                            }
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.setTitle("Добавление отзыва");
                            stage.getIcons().add(new Image("file:picture/addfeedback.png"));
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    menuItem3.setOnAction(event -> {
                        try {
                            Stage currentStage = (Stage) menuItem3.getParentPopup().getOwnerWindow();
                            currentStage.close();
                            // Получить id выбранной книги
                            ProductData selectedBook = idspis.getSelectionModel().getSelectedItem();
                            if (selectedBook != null) {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("Stranic.fxml"));
                                Parent root = loader.load();
                                Stranic controller = loader.getController();
                                try {
                                    controller.setBookId(selectedBook.getId());
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                } catch (ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.setTitle("Страницы");
                                stage.getIcons().add(new Image("file:picture/stranic.png"));
                                stage.show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    contextMenu.getItems().addAll(menuItem1, menuItem2, menuItem3);
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
