package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    public static ArrayList<ArrayList<String>> sendQuery(String query) throws SQLException {
        return sendQuery(query, new ArrayList<>());
    }

    public static ArrayList<ArrayList<String>> sendQuery(String query, Object param) throws SQLException {
        List<Object> params = new ArrayList<>();
        params.add(param);
        return sendQuery(query, params);
    }

    public static ArrayList<ArrayList<String>> sendQuery(String query, List<Object> params) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection();
                PreparedStatement statement = conn.prepareStatement(query)) {

            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }

            boolean hasResult = statement.execute();

            if (hasResult) {

                ResultSet res = statement.executeQuery();
                ArrayList<ArrayList<String>> data = new ArrayList<>();

                while (res.next()) {
                    ArrayList<String> row = new ArrayList<>();
                    int columnCount = res.getMetaData().getColumnCount();

                    for (int i = 1; i <= columnCount; i++) {
                        row.add(res.getString(i));
                    }

                    data.add(row);
                }

                res.close();
                return data;
            } else {
                statement.getUpdateCount();
                return new ArrayList<>();
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            throw e;
        }
    }
}
