package com.example.kyrs;
//Класс необходимый для возврата данных в ComboBox класса Redaktirovanie
public class Publisher {
        private final int id;
        private final String name;
        public Publisher(int id, String name) {
            this.id = id;
            this.name = name;
        }
        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        @Override
        public String toString() {
            return id + " - " + name;
        }
    }
