package com.example.kyrs;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

// Контроллер для управления капчей
public class CaptchaController {

    private static final int captchaLength = 6;

    @FXML
    private Canvas canvas;
    @FXML
    private TextField textInputField;

    private CaptchaGenerator captchaGenerator;
    private String captchaText;
    @FXML
    private Button proverka;
    private boolean isBlocked = false;
    private long blockStartTime = 0;
    private final long BLOCK_DURATION = 10000;

    // Метод для проверки капчи
    @FXML
    public void prover() {
        proverka.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (captchaText.equals(textInputField.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Успех");
                alert.setHeaderText("Капча введена верно!");
                alert.showAndWait();
            } else {
                regenerateCaptcha(); // Перегенерация капчи
                isBlocked = true;
                blockStartTime = System.currentTimeMillis(); // Устанавливаем временную метку начала блокировки
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Капча введена неправильно. Вход заблокирован на 10 секунд");
                alert.showAndWait();
            }
        });
    }

    // Метод для валидации капчи
    public boolean validate() {
        if (!Objects.equals(this.captchaText, this.textInputField.getText())) {
            regenerateCaptcha();
            this.textInputField.setText("Всё верно");
            return false;
        }
        return true;
    }

    // Инициализация контроллера
    @FXML
    private void initialize() {
        this.captchaGenerator = new CaptchaGenerator(this.canvas); // Создание генератора капчи
        regenerateCaptcha(); // Перегенерация капчи
    }

    // Метод для перегенерации капчи
    private void regenerateCaptcha() {
        this.captchaText = this.captchaGenerator.generate(captchaLength); // Генерация новой капчи
    }
}