package com.example.kyrs;
//Класс необходимый для возврата данных в класс StrStranic
public class StranicData {
    private int id;
    private final String photo;

    public StranicData(int id, String photo) {
        this.id = id;
        this.photo = photo;
    }

    public int getId() {
        return this.id;  // Получение идентификатора страницы
    }
    public String getPhoto() {
        return this.photo;  // Получение пути к фото страницы
    }
}
