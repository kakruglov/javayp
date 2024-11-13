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
    
    public class AdminKnigi {
        @FXML
        private ListView<ProductData> idspis;
        @FXML
        private ComboBox<String> idCategory;
        @FXML
        private Button addOrder;
        @FXML
        private TextField TextFieldPoisk;
        DB db = new DB();
        @FXML
        void initialize() throws SQLException, ClassNotFoundException {

            ObservableList<String> categor = db.getCategori();
            categor.addAll("Сбросить фильтр");
            idCategory.setItems(categor);

            // Инициируем объект
            db = new DB();
            //кнопка сброса фильтра

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
                        // Получаем все продукты для выбранной категории
                        List<ProductData> productsInCategory = db.getProductsByCategory(selectedCategoryId);
                        // Обновляем список продуктов в ListView
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

            // метод для кнопки открывающий форму для создания заказа
            addOrder.setOnAction(event -> {
                try {
                    // Загрузите FXML-файл
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Order.fxml"));
                    Parent root = loader.load();
                    // Получите контроллер из FXML-файла
                    Order controller = loader.getController();
                    // Создайте новую сцену с загруженным корневым элементом
                    Scene scene = new Scene(root);
                    // Создайте новое окно Stage
                    Stage stage = new Stage();
                    // Установите заголовок окна
                    stage.setTitle("Новый заказ");
                    stage.getIcons().add(new Image("file:picture/addorders.png"));
                    // Установите сцену для окна Stage
                    stage.setScene(scene);
                    // Покажите окно
                    stage.show();
                    // Закройте текущую форму AdminKnigi.fxml
                    Stage currentStage = (Stage) addOrder.getScene().getWindow();
                    currentStage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            // Метод для подгрузки заданий внутрь программы
            loadInfo();
        }
        void loadInfo() {
            try {
                List<ProductData> ls = db.getProduct();
                idspis.getItems().clear();
                idspis.getItems().addAll(ls);
                idspis.setCellFactory(stringListView -> {
                    ListCell<ProductData> cell = new StrProd();
                    ContextMenu contextMenu = new ContextMenu();
                    MenuItem menuItem1 = new MenuItem("Посмотреть отзывы");
                    MenuItem menuItem2 = new MenuItem("Добавить отзыв");
                    MenuItem menuItem3 = new MenuItem("Посмотреть страницы");
                    MenuItem menuItem4 = new MenuItem("Добавить товар");
                    MenuItem menuItem5 = new MenuItem("Удалить товар");
                    MenuItem menuItem6 = new MenuItem("Редактировать");
                    menuItem1.setOnAction(event -> {
                        try {
                            Stage currentStage = (Stage) menuItem1.getParentPopup().getOwnerWindow();
                            currentStage.close();
                            // Получить id выбранной книги
                            ProductData selectedBook = idspis.getSelectionModel().getSelectedItem();
                            if (selectedBook != null) {
                                // Открыть форму feedbackOsn.fxml
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("checkotzbIv.fxml"));
                                Parent root = loader.load();
                                // Получить контроллер из FXML-файла
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
                            // Открыть форму feedback.fxml
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("addotzbIv.fxml"));
                            Parent root = loader.load();
                            // Получить контроллер из FXML-файла
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
                                // Открыть форму Stranic.fxml
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("Stranic.fxml"));
                                Parent root = loader.load();
                                // Получить контроллер из FXML-файла StranicController
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
                    menuItem4.setOnAction(event -> {
                        try {
                            Stage currentStage = (Stage) menuItem4.getParentPopup().getOwnerWindow();
                            currentStage.close();
                            Parent root = FXMLLoader.load(getClass().getResource("dobavlenie.fxml"));
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.setTitle("Добавление товара");
                            stage.getIcons().add(new Image("file:picture/addbook.png"));
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    menuItem5.setOnAction(event -> {
                        try {
                            // Получить id выбранной книги
                            ProductData selectedProduct = idspis.getSelectionModel().getSelectedItem();
                            if (selectedProduct != null) {
                                // Удалить выбранный товар из базы данных
                                db.deleteProduct(selectedProduct.getId());
                                // Обновить список товаров
                                loadInfo();
                            }
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Успех");
                            alert.setHeaderText(null);
                            alert.setContentText("Товар успешно удалён!");
                            alert.showAndWait();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    menuItem6.setOnAction(event -> {
                        try {
                            // Закрыть текущую форму AdminKnigi.fxml
                            Stage currentStage = (Stage) menuItem6.getParentPopup().getOwnerWindow();
                            currentStage.close();
                            // Открыть форму redaktir.fxml
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("redaktir.fxml"));
                            Parent root = loader.load();
                            // Получить контроллер из FXML-файла
                            Redactirovanie controller = loader.getController();
                            // Получить id выбранной книги
                            ProductData selectedBook = idspis.getSelectionModel().getSelectedItem();
                            if (selectedBook != null) {
                                controller.setBookId(selectedBook.getId());
                                controller.setDb(db);
                                controller.loadBookData();
                            }
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.setTitle("Редактирование товара");
                            stage.getIcons().add(new Image("file:picture/redaktbook.png"));
                            stage.show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    contextMenu.getItems().addAll(menuItem1, menuItem2, menuItem3,menuItem4,menuItem5, menuItem6);
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
