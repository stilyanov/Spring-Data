package DBApps.Exercise;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P09_IncreaseAgeStoredProcedure {
    public static void main(String[] args) throws SQLException {
        DBTools dbTools = new DBTools();

        Scanner scanner = new Scanner(System.in);
        int minionId = Integer.parseInt(scanner.nextLine());

        CallableStatement callUspGetOlder = dbTools.getConnection().prepareCall("CALL usp_get_older(?)");

        callUspGetOlder.setInt(1, minionId);

        ResultSet resultSet = callUspGetOlder.executeQuery();

        resultSet.next();

        System.out.printf("%s %d", resultSet.getString(1), resultSet.getInt(2));

    }
}
