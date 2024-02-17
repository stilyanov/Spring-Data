package DBApps.Exercise;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;

public class P07_PrintAllMinionNames {
    public static void main(String[] args) throws SQLException {
        DBTools dbTools = new DBTools();

        ResultSet resultSet = dbTools.getConnection().createStatement().executeQuery("SELECT name FROM minions");

        ArrayDeque<String> deque = new ArrayDeque<>();
        while (resultSet.next()) {
            deque.add(resultSet.getString("name"));
        }

        while (!deque.isEmpty()) {
            System.out.println(deque.removeFirst());
            if (!deque.isEmpty()) {
                System.out.println(deque.removeLast());
            }
        }
    }
}
