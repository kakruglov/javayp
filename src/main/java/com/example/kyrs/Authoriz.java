package com.example.kyrs;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class Authoriz {
    @FXML
    private TextField idPassL;
    @FXML
    private CheckBox showPassword;
    @FXML
    private TextField idLog;
    @FXML
    private PasswordField idPass;
    @FXML
    private Button idVhod;
    public static String currentLogin;
    public static String currentPassword;
    DB db = null;
    private boolean isBlocked = false;
    @FXML
    void initialize() {
        //Просмотр пароля с помощью CheckBox
        idPass.setVisible(true);
        idPassL.setVisible(false);
        showPassword.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                idPassL.setText(idPass.getText());
                idPassL.setVisible(true);
                idPass.setText(idPassL.getText());
            } else {
                idPass.setText(idPassL.getText());
                idPassL.setVisible(false);
            }
        });
        // Инициируем объект
        db = new DB();
        // Обработчик события. Сработает при нажатии на кнопку
        idVhod.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                currentLogin = idLog.getText();
                currentPassword = idPass.getText();

                if (isBlocked) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Отказано");
                    alert.setHeaderText(null);
                    alert.setContentText("Капча не была введена!");
                    alert.showAndWait();
                    return;
                }
                try {
                    // Проверяем является ли поле заполненным
                    if (!idLog.getText().trim().equals("") & !idPass.getText().trim().equals("")) {
                        int a = db.getUser(idLog.getText(), idPass.getText());
                        if (a == 1) {
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("adminknigi.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(fxmlLoader.load());
                            stage.setTitle("Просмотр списка");
                            stage.getIcons().add(new Image("C:\\Users\\User\\Desktop\\kyrs\\src\\main\\resources\\com\\example\\kyrs\\checkbook.png"));
                            stage.setScene(scene);
                            stage.show();
                            ((Stage) idVhod.getScene().getWindow()).close();
                        }
                        if (a == 2) {
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("klientknigi.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(fxmlLoader.load());
                            stage.setTitle("Просмотр списка");
                            stage.getIcons().add(new Image("C:\\Users\\User\\Desktop\\kyrs\\src\\main\\resources\\com\\example\\kyrs\\checkbook.png"));
                            stage.setScene(scene);
                            stage.show();
                            ((Stage) idVhod.getScene().getWindow()).close();
                        }
                        if (a == 0) {
                            startTimer();
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("captcha.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(fxmlLoader.load(), 252, 167);
                            stage.setTitle("Проверка капчей");
                            stage.getIcons().add(new Image("file:captcha.png"));
                            stage.setScene(scene);
                            stage.show();
                        }
                    }
                } catch (SQLException e) { // Отслеживаем ошибки
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    //запуск таймера на 10 секунд
    private void startTimer() {
        isBlocked = true;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isBlocked = false;
            }
        }, 10000);
    }
}

