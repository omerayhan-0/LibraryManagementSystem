package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // ---- 1) Singleton Instance ----
    private static DatabaseConnection instance;

    // ---- 2) Tek bir Connection nesnesi ----
    private Connection connection;

    // ---- 3) Bağlantı bilgileri ----
    private final String URL = "jdbc:mysql://localhost:3306/kutuphane_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private final String USER = "root";
    private final String PASSWORD = "";  // WAMP için

    // ---- 4) Constructor private → dışarıdan new yapılamaz ----
    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("MySQL bağlantısı başarılı!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("MySQL bağlantısı başarısız!");
        }
    }

    // ---- 5) Dışarıdan çağrılan Singleton methodu ----
    public static DatabaseConnection getInstance() {
        if (instance == null) {                     // 1. kontrol
            synchronized (DatabaseConnection.class) {
                if (instance == null) {             // 2. kontrol (Double-Check Lock)
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    // ---- 6) Controller'ların kullanacağı bağlantı ----
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
