package DBApps.Exercise;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class P05_ChangeTownNamesCasing {
    public static void main(String[] args) throws SQLException {
        DBTools dbTools = new DBTools();
        Scanner scanner = new Scanner(System.in);

        String country = scanner.nextLine();

        PreparedStatement preparedStatement = dbTools.getConnection().prepareStatement
                ("UPDATE towns " +
                        "SET name = UPPER(name) " +
                        "WHERE country = ?");
        preparedStatement.setString(1, country);

        int townsChanged = preparedStatement.executeUpdate();

        if (townsChanged == 0) {
            System.out.println("No town names were affected.");
        } else {
            System.out.printf("%d town names were affected.%n", townsChanged);

            preparedStatement = dbTools.getConnection().prepareStatement("SELECT name FROM towns WHERE country = ?");
            preparedStatement.setString(1, country);

            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<String> towns = new ArrayList<>();

            while (resultSet.next()){
                towns.add(resultSet.getString("name"));
            }

            System.out.printf("[%s]", String.join(", ",  towns));
        }
    }
}
