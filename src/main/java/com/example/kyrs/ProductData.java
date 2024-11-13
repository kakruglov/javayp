package com.example.kyrs;
//Класс необходимый для возврата данных в класс StrProd
public class ProductData {
    private int id;
    private final String name;
    private final String photo;
    private final String opisanie;
    private final String price;
    private final String nalichie;
    public ProductData(int id, String name, String photo, String opisanie, String price, String nalichie) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.opisanie = opisanie;
        this.price = price;
        this.nalichie = nalichie;
    }
    public int getId(){
        return this.id;
    } // Получение идентификатора книги

    public String getName() {
        return this.name;
    } // Получение названия книги
    public String getPhoto() {
        return this.photo;
    }// Получение пути к фото книги
    public String getOpisanie() {
        return this.opisanie;
    }// Получение описания книги
    public String getPrice() {
        return this.price;
    }// Получение цены книги
    public String getNalichie(){
        return this.nalichie;
    }// Получение наличия книги
}