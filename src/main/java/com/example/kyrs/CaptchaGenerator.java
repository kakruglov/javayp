package com.example.kyrs;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Random;

public class CaptchaGenerator {

    // Массив символов, используемых при генерации капчи
    private static final char[] captchaSymbols = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };
    private final Canvas canvas;
    private final GraphicsContext gc;
    // Конструктор класса CaptchaGenerator
    public CaptchaGenerator(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }
    // Метод для генерации капчи заданной длины
    public String generate(int length) {
        String text = generateCaptchaText(length);

        int width = (int) this.canvas.getWidth();
        int height = (int) this.canvas.getHeight();

        // Закрашиваем холст белым цветом
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);

        Random random = new Random();

        gc.setFont(new Font("Arial", height / 2));

        // Рисуем символы капчи со случайным смещением по высоте
        int symbolOffset = width / length;
        for (int i = 0; i < length; i++) {
            gc.setFill(new Color(random.nextDouble() * 0.5f, random.nextDouble() * 0.5f, random.nextDouble() * 0.5f, 0.5f + random.nextDouble() * 0.25f));
            gc.fillText(String.valueOf(text.charAt(i)), i * symbolOffset + (symbolOffset >> 1), height / 2 + random.nextInt(height / 2));
        }

        // Рисуем шумовые линии
        gc.beginPath();
        for (int i = 0; i < 3; i++) {
            gc.setFill(new Color(random.nextDouble() * 0.5f, random.nextDouble() * 0.5f, random.nextDouble() * 0.5f, 0.5f + random.nextDouble() * 0.25f));
            gc.moveTo(0, random.nextInt(height));
            gc.lineTo(width, random.nextInt(height));
        }
        gc.stroke();

        // Рисуем шумовые круги
        for (int i = 0; i < 7; i++) {
            gc.setFill(new Color(random.nextDouble() * 0.5f, random.nextDouble() * 0.5f, random.nextDouble() * 0.5f, 0.5f + random.nextDouble() * 0.25f));
            gc.fillOval(random.nextInt(width), random.nextInt(height), width / 10, width / 10);
        }

        return text;
    }

    // Метод для генерации текста капчи
    private String generateCaptchaText(int length) {
        StringBuilder builder = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) builder.append(captchaSymbols[random.nextInt(captchaSymbols.length)]);

        return builder.toString(); // Возвращаем сгенерированный текст капчи
    }
}