package DBApps.Exercise;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class P03_GetMinionsNames {
    public static void main(String[] args) throws SQLException {
        DBTools dbTools = new DBTools();

        Scanner scanner = new Scanner(System.in);
        int villainId = Integer.parseInt(scanner.nextLine());

        PreparedStatement preparedStatement = dbTools.getConnection().prepareStatement
                ("SELECT name FROM villains WHERE id = ?");
        preparedStatement.setInt(1, villainId);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            System.out.printf("No villain with ID %d exists in the database.", villainId);
            return;
        }

        String villainName = resultSet.getString("name");
        System.out.println("Villain: " + villainName);

        preparedStatement = dbTools.getConnection().prepareStatement
                ("SELECT v.name, m.name, m.age " +
                        "FROM villains v " +
                        "JOIN minions_villains mv on v.id = mv.villain_id " +
                        "JOIN minions m on mv.minion_id = m.id " +
                        "WHERE v.id = ?");
        preparedStatement.setInt(1, villainId);
        resultSet = preparedStatement.executeQuery();

        int index = 0;
        while (resultSet.next()) {
            index++;
            System.out.printf("%d. %s %d%n", index, resultSet.getString("m.name"), resultSet.getInt("m.age"));
        }

    }
}
