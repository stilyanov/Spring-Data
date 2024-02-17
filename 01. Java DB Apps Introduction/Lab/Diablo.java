package DBApps.Lab;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Diablo {
    public static void main(String[] args) throws SQLException {
        // Connect to DB
        Properties properties = new Properties();
        properties.setProperty("user", "");
        properties.setProperty("password", "");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/diablo", properties);

        // Execute Query
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Username: ");
        String userName = scanner.nextLine();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT u.id, u.first_name, u.last_name, COUNT(ug.game_id) " +
                        "FROM users AS u " +
                        "JOIN users_games AS ug ON ug.user_id = u.id " +
                        "WHERE u.user_name = ?");

        preparedStatement.setString(1, userName);

        ResultSet resultSet = preparedStatement.executeQuery();

        // Print Result
        resultSet.next();
        Object userId = resultSet.getObject(1);

        if (userId != null) {
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            int gameId = resultSet.getInt(4);

            System.out.printf("User: %s%n%s %s has played %d games", userName, firstName, lastName, gameId);
        } else {
            System.out.println("No such user exists");
        }
    }
}
