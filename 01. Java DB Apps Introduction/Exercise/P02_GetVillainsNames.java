package DBApps.Exercise;

import java.sql.*;
import java.util.Properties;

public class P02_GetVillainsNames {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "1234554321");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery
                ("SELECT v.name, COUNT(mv.minion_id) AS count " +
                        "FROM villains v " +
                        "JOIN minions_villains mv on v.id = mv.villain_id " +
                        "GROUP BY v.name " +
                        "HAVING count > 15 " +
                        "ORDER BY count DESC");

        while (resultSet.next()) {
            System.out.printf("%s %d%n", resultSet.getString("v.name"), resultSet.getInt("count"));
        }
    }
}
