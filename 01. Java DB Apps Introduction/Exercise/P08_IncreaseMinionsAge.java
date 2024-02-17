package DBApps.Exercise;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class P08_IncreaseMinionsAge {
    public static void main(String[] args) throws SQLException {
        DBTools dbTools = new DBTools();

        Scanner scanner = new Scanner(System.in);
        int[] minionsIds = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        for (int minionsId : minionsIds) {
            PreparedStatement preparedStatement = dbTools.getConnection()
                    .prepareStatement("UPDATE minions SET name = LOWER(name), age  = age + 1 WHERE id = ?");

            preparedStatement.setInt(1, minionsId);
            preparedStatement.executeUpdate();
        }

        PreparedStatement preparedStatement = dbTools.getConnection().prepareStatement("SELECT name, age FROM minions");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s %d%n", resultSet.getString("name"), resultSet.getInt("age"));
        }
    }
}
