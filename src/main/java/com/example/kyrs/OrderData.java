package com.example.kyrs;
//Класс необходимый для возврата данных в класс StrOrder
public class OrderData {
    private final String name;
    private final String photo;
    private final String opisanie;
    private final String price;
        public OrderData(String name, String photo, String opisanie, String price) {
            this.name = name;
            this.photo = photo;
            this.opisanie = opisanie;
            this.price = price;
        }
    public String getName() {
        return this.name;
    }// Получение имени товара
    public String getPhoto() {
        return this.photo;
    }// Получение пути к фото товара
    public String getOpisanie() {
        return this.opisanie;
    }// Получение описание товара
    public String getPrice() {
        return this.price;
    }// Получение цены товара
    }


