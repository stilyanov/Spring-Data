package DBApps.Exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBTools {
    private final String url = "jdbc:mysql://localhost:3306/";
    private Connection connection;

    public DBTools() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "");
        properties.setProperty("password", "");

        connection = DriverManager.getConnection(url, properties);
    }

    public Connection getConnection() {
        return connection;
    }
}
