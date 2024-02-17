package DBApps.Lab;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        // Connect to DB
        Properties properties = new Properties();
        properties.setProperty("user", "");
        properties.setProperty("password", "");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/soft_uni", properties);

        // Execute Query
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter salary: ");
        String salary = scanner.nextLine();

        PreparedStatement preparedStatement = connection.prepareStatement
                ("SELECT first_name, last_name, salary FROM employees WHERE salary > ?");

        preparedStatement.setDouble(1, Double.parseDouble(salary));

        ResultSet resultSet = preparedStatement.executeQuery();

        // Print Result
        while (resultSet.next()) {
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            double currentSalary = resultSet.getDouble("salary");

            System.out.printf("%s %s -> Salary is %.2f%n", firstName, lastName, currentSalary);
        }

    }
}
