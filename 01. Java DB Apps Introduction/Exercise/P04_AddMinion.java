package DBApps.Exercise;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P04_AddMinion {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        String[] minionsTokens = scanner.nextLine().split(" ");
        String minionName = minionsTokens[1];
        int minionAge = Integer.parseInt(minionsTokens[2]);
        String townName = minionsTokens[3];

        String villainName = scanner.nextLine().split(" ")[1];

        int townId = findTownNameById(townName);

        if (townId == 0) {
            townId = createTown(townName);
            System.out.printf("Town %s was added to the database.%n", townName);
        }

        int minionId = createMinion(minionName, minionAge, townId);
        int villainId = findVillainIdByName(villainName);
        if (villainId == 0) {
            villainId = createVillain(villainName);
            System.out.printf("Villain %s was added to the database.%n", villainName);
        }

        populateMinionsVillains(minionId, villainId);
        System.out.printf("Successfully added %s to be minion of %s.", minionName, villainName);

    }

    private static void populateMinionsVillains(int minionId, int villainId) throws SQLException {
        DBTools dbTools = new DBTools();
        PreparedStatement preparedStatement = dbTools.getConnection().prepareStatement("INSERT INTO minions_villains(minion_id, villain_id) VALUE(?, ?)");

        preparedStatement.setInt(1, minionId);
        preparedStatement.setInt(2, villainId);
        preparedStatement.executeUpdate();
    }

    private static int createVillain(String villainName) throws SQLException {
        DBTools dbTools = new DBTools();
        PreparedStatement preparedStatement = dbTools.getConnection().prepareStatement("INSERT INTO villains(name, evilness_factor) VALUE (?, 'evil')");

        preparedStatement.setString(1, villainName);
        preparedStatement.executeUpdate();

        preparedStatement = dbTools.getConnection().prepareStatement("SELECT id FROM villains WHERE name = ?");
        preparedStatement.setString(1, villainName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        return 0;
    }

    private static int findVillainIdByName(String villainName) throws SQLException {
        DBTools dbTools = new DBTools();
        PreparedStatement preparedStatement = dbTools.getConnection().prepareStatement("SELECT id FROM villains WHERE name = ?");

        preparedStatement.setString(1, villainName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }

        return 0;
    }

    private static int createMinion(String minionName, int minionAge, int townId) throws SQLException {
        DBTools dbTools = new DBTools();
        PreparedStatement preparedStatement = dbTools.getConnection()
                .prepareStatement("INSERT INTO minions(name, age, town_id) VALUE(?, ?, ?)");

        preparedStatement.setString(1, minionName);
        preparedStatement.setInt(2, minionAge);
        preparedStatement.setInt(3, townId);
        preparedStatement.executeUpdate();

        preparedStatement = dbTools.getConnection().prepareStatement("SELECT id FROM minions WHERE name = ?");
        preparedStatement.setString(1, minionName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        return resultSet.getInt("id");
    }

    private static int createTown(String townName) throws SQLException {
        DBTools dbTools = new DBTools();
        PreparedStatement preparedStatement = dbTools.getConnection().prepareStatement("INSERT INTO towns(name) VALUE(?)");
        preparedStatement.setString(1, townName);
        preparedStatement.executeUpdate();

        return 0;
    }

    private static int findTownNameById(String townName) throws SQLException {
        DBTools dbTools = new DBTools();
        PreparedStatement preparedStatement = dbTools.getConnection().prepareStatement("SELECT id FROM towns WHERE name = ?");

        preparedStatement.setString(1, townName);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        return 0;
    }
}
