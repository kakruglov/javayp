package com.example.kyrs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;

public class DB {
    private final String HOST = "127.0.0.1";
    private final String PORT = "13306";
    private final String DB_NAME = "javafxTest";
    private final String LOGIN = "kirill";
    private final String PASS = "123321";
    public Connection dbConn = null;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?characterEncoding=UTF8";
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }
    // Метод для получения информации о страничных данных по идентификатору книги
    public ArrayList<StranicData> getStranicByBookId(int bookId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM stranic WHERE book_id = ?";

        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, bookId);
        ResultSet res = statement.executeQuery();
        ArrayList<StranicData> stranic = new ArrayList<>();

        while (res.next())
            stranic.add(new StranicData(res.getInt("id"),res.getString("photo_name")));
        return stranic;
    }
    // Метод для проверки учетных данных пользователя и возврата роли
    public int getUser(String log, String pass) throws SQLException, ClassNotFoundException {
        String sql = "SELECT role_id, count(*) as n FROM users where userlogin=? and userpassword=? group by role_id";

        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, log);
        statement.setString(2, pass);
        ResultSet res = statement.executeQuery();

        int coll = 0;
        int role = 0;
        while (res.next()) {
            coll = res.getInt("n");
            role = res.getInt("role_id");
        }
        return role;
    }
    // Метод для получения списка всех продуктов из базы данных
    public ArrayList<ProductData> getProduct() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM products ORDER BY id DESC";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        ArrayList<ProductData> stud = new ArrayList<>();
        while(res.next())
            stud.add(new ProductData(res.getInt("id"),res.getString("name"),res.getString("photo"),res.getString("opisanie"),res.getString("price"),res.getString("nalichie")));
        return stud;
    }
    // Метод для получения списка продуктов, которые имеются в наличии
    public ArrayList<OrderData> getProducts() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM products WHERE nalichie = 'Есть в наличии' ORDER BY id DESC";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        ArrayList<OrderData> order = new ArrayList<>();
        while(res.next())
            order.add(new OrderData(res.getString("name"),res.getString("photo"),res.getString("opisanie"),res.getString("price")));
        return order;
    }
    // Метод для получения списка продуктов по их категории
    public ArrayList<ProductData> getProductsByCategory(int categoryId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM products WHERE category_id = ?";

        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, categoryId);
        ResultSet res = statement.executeQuery();

        ArrayList<ProductData> products = new ArrayList<>();
        while(res.next())
            products.add(new ProductData(res.getInt("id"),res.getString("name"),res.getString("photo"),res.getString("opisanie"),res.getString("price"),res.getString("nalichie")));
        return products;
    }
    // Метод для получения идентификатора последнего продукта
    public int getLastProduct() throws SQLException, ClassNotFoundException {
        String sql = "SELECT MAX(id) FROM products";
        try (PreparedStatement statement = getDbConnection().prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            if (result.next()) {
                return result.getInt(1);
            } else {
                return 0;
            }
        }
    }
    // Метод для получения списка пунктов выдачи
    public ObservableList<String> getPickupPoint() throws SQLException, ClassNotFoundException {
        ObservableList<String> pickup = FXCollections.observableArrayList();
        try {
            Statement statement = getDbConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT id,name  FROM pickup_point");
            while (result.next()) {
                int id = result.getInt("id");
                String cat = result.getString("name");
                pickup.add(id + " - " + cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pickup;
    }
    // Метод проверки, существует ли продукт с определенным идентификатором
    public boolean productExists(int id_products) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM products WHERE id = ?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, id_products);
        ResultSet res = statement.executeQuery();
        return res.next();
    }
    // Метод для получения списка категорий
    public ObservableList<String> getCategori() throws SQLException, ClassNotFoundException {
        ObservableList<String> izdat = FXCollections.observableArrayList();
        try {
            Statement statement = getDbConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT id,name  FROM categories");
            while (result.next()) {
                int id = result.getInt("id");
                String izdatel = result.getString("name");
                izdat.add(id + " - " + izdatel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return izdat;
    }
    // Метод сохранения товара в базе данных
    public void saveOrder(Integer id, String name, String price, String opisanie, String nalichie, int authorId, int categorId, int izdatelId) {
        try {
            String sql = "INSERT INTO products (id, name, price, opisanie,nalichie, author_id, category_id, izdatelstvo_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setInt(1, id);
            prSt.setString(2, name);
            prSt.setString(3, price);
            prSt.setString(4, opisanie);
            prSt.setString(5, nalichie);
            prSt.setInt(6, authorId);
            prSt.setInt(7, categorId);
            prSt.setInt(8,izdatelId);
            prSt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Способ добавления отзыва о конкретном продукте
    public void addFeedBack(String feedback, int id_products) throws SQLException, ClassNotFoundException {
        // Проверяем, существует ли id_products в таблице products
        String sql = "SELECT * FROM products WHERE id = ?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, id_products);
        ResultSet res = statement.executeQuery();

        if (!res.next()) {
            throw new SQLException("id_products не существует в таблице products");
        }

        // Если id_products существует, добавляем отзыв
        sql = "INSERT INTO feedback (feedback, id_products) VALUES (?, ?)";
        try (PreparedStatement stmt = getDbConnection().prepareStatement(sql)) {
            stmt.setString(1, feedback);
            stmt.setInt(2, id_products);
            stmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    // Метод для получения списка авторов книг
public ObservableList<Publisher> getAuthor() throws SQLException, ClassNotFoundException {
    ObservableList<Publisher> author = FXCollections.observableArrayList();
    try {
        Statement statement = getDbConnection().createStatement();
        ResultSet result = statement.executeQuery("SELECT id,name  FROM authors");
        while (result.next()) {
            int id = result.getInt("id");
            String aut = result.getString("name");
            author.add(new Publisher(id, aut));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return author;
}
    // Метод для получения списка издательств
    public ObservableList<Publisher> getIzdatel() throws SQLException, ClassNotFoundException {
        ObservableList<Publisher> izdat = FXCollections.observableArrayList();
        try {
            Statement statement = getDbConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT id,name FROM izdatelstvo");
            while (result.next()) {
                int id = result.getInt("id");
                String izdatel = result.getString("name");
                izdat.add(new Publisher(id, izdatel));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return izdat;
    }
    // Метод для получения списка категорий книг
    public ObservableList<Publisher> getCategor() throws SQLException, ClassNotFoundException {
        ObservableList<Publisher> categor = FXCollections.observableArrayList();
        try {
            Statement statement = getDbConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT id,name  FROM categories");
            while (result.next()) {
                int id = result.getInt("id");
                String cat = result.getString("name");
                categor.add(new Publisher(id, cat));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categor;
    }
    // Метод для добавления отзыва к книге с определенным идентификатором
    public ArrayList<String> getFeedbackForBook(int bookId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT feedback FROM feedback WHERE id_products = ?";

        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, bookId);
        ResultSet res = statement.executeQuery();

        ArrayList<String> feedback = new ArrayList<>();
        while(res.next())
            feedback.add(res.getString("feedback"));
        return feedback;
    }
    //Метод для добавления нового заказа
    public void addOrder(int pickupPointId, double totalPrice, ArrayList<String> products) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO orders (pickup_poind_id, total_price) VALUES (?, ?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        prSt.setInt(1, pickupPointId);
        prSt.setDouble(2, totalPrice);
        prSt.executeUpdate();
        // Получаем id последней добавленной записи
        ResultSet generatedKeys = prSt.getGeneratedKeys();
        if (generatedKeys.next()) {
            int orderId = generatedKeys.getInt(1);

            // Создаем SQL-запрос для добавления данных в таблицу products_has_order
            sql = "INSERT INTO products_has_order (order_id, product_id, kol_vo) VALUES (?, ?, ?)";
            prSt = getDbConnection().prepareStatement(sql);
            for (String product : products) {
                String[] parts = product.split(" x ");
                String productName = parts[0];
                int quantity = Integer.parseInt(parts[1]);
                // Получаем id продукта по его имени
                sql = "SELECT id FROM products WHERE name = ?";
                PreparedStatement stmt = getDbConnection().prepareStatement(sql);
                stmt.setString(1, productName);
                ResultSet res = stmt.executeQuery();
                if (res.next()) {
                    int productId = res.getInt("id");
                    // Добавляем данные в таблицу products_has_order
                    prSt.setInt(1, orderId);
                    prSt.setInt(2, productId);
                    prSt.setInt(3, quantity);
                    prSt.executeUpdate();
                }
            }
        }
    }
    // Удаление товара с помощью хранимой процедуры
    public void deleteProduct(int id) throws SQLException, ClassNotFoundException {
        String sql = "{call DeleteBook(?)}";
        CallableStatement statement = getDbConnection().prepareCall(sql);
        statement.setInt(1, id);
        statement.execute();
    }
}

