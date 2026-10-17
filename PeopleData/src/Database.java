import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {
    private Connection connection;
    private Statement statement;

    public Database() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db_people?useSSL=false&serverTimezone=Asia/Jakarta",
                    "root",
                    ""
            );
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Koneksi database gagal. Pastikan MySQL XAMPP aktif dan database db_people tersedia.", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    // Metode SELECT sesuai pola pada Modul 5.
    public ResultSet selectQuery(String sql) {
        try {
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Metode INSERT, UPDATE, dan DELETE sesuai pola pada Modul 5.
    public int insertUpdateDeleteQuery(String sql) {
        try {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
