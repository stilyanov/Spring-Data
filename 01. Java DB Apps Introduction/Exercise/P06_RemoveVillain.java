package DBApps.Exercise;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P06_RemoveVillain {
    public static void main(String[] args) throws SQLException {
        DBTools dbTools = new DBTools();
        Scanner scanner = new Scanner(System.in);

        int villainId = Integer.parseInt(scanner.nextLine());

        PreparedStatement preparedStatement = dbTools.getConnection().prepareStatement("SELECT name FROM villains WHERE id = ?");
        preparedStatement.setInt(1, villainId);

        ResultSet resultSet = preparedStatement.executeQuery();

        String villainName = "";
        if (resultSet.next()) {
            villainName = resultSet.getString("name");
        }

        if (villainName.isEmpty()) {
            System.out.println("No such villain was found");
        } else {
            preparedStatement = dbTools.getConnection().prepareStatement("DELETE FROM minions_villains WHERE villain_id = ?");
            preparedStatement.setInt(1, villainId);
            int releasedMinions = preparedStatement.executeUpdate();

            preparedStatement = dbTools.getConnection().prepareStatement("DELETE FROM villains WHERE id = ?");
            preparedStatement.setInt(1, villainId);
            preparedStatement.executeUpdate();

            System.out.printf("%s was deleted%n%d minions released", villainName, releasedMinions);
        }
    }
}
