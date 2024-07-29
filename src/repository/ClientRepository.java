package repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Client;
import util.DatabaseManager;

public class ClientRepository {

    public List<Client> getAllClients() throws SQLException {
        String query = "SELECT * FROM clients WHERE deleted != 1";
        ArrayList<ArrayList<String>> res = DatabaseManager.sendQuery(query);

        return mapRawDataToClients(res);
    }

    public Client getClientById(int id) throws SQLException {
        String query = "SELECT * FROM clients WHERE id = ? AND deleted != 1";

        ArrayList<ArrayList<String>> res = DatabaseManager.sendQuery(query, id);
        List<Client> clients = mapRawDataToClients(res);

        return clients.isEmpty() ? null : clients.get(0);
    }

    public int getClientIdByName(String name, String lastname) throws SQLException {
        String query = "SELECT id FROM clients WHERE name = ? AND lastname = ? AND deleted != 1";

        List<Object> params = new ArrayList<>();
        params.add(name);
        params.add(lastname);

        ArrayList<ArrayList<String>> res = DatabaseManager.sendQuery(query, params);
        List<Client> clients = mapRawDataToClients(res);

        return clients.get(0).getId();
    }

    private List<Client> mapRawDataToClients(ArrayList<ArrayList<String>> rawData) {
        List<Client> clients = new ArrayList<>();

        for (ArrayList<String> row : rawData) {
            Client client = new Client(
                    Integer.parseInt(row.get(0)),
                    row.get(1),
                    row.get(2),
                    row.get(3),
                    Integer.parseInt(row.get(4)));
            clients.add(client);
        }

        return clients;
    }

    public void saveClient(Client client) throws SQLException {
        String query = "INSERT INTO clients (name, lastname, cellphone, trainingSessionsPerWeek) VALUES (?, ?, ?, ?)";
        List<Object> params = new ArrayList<>();
        params.add(client.getName());
        params.add(client.getLastname());
        params.add(client.getCellphone());
        params.add(client.getTrainingSessionsPerWeek());
        DatabaseManager.sendQuery(query, params);
    }

    public void updateClient(Client client) throws SQLException {
        String query = "UPDATE clients set name = ?, lastname = ?, cellphone = ?, trainingSessionsPerWeek = ? " +
                "WHERE id = ? AND deleted != 1";

        List<Object> params = new ArrayList<>();
        params.add(client.getName());
        params.add(client.getLastname());
        params.add(client.getCellphone());
        params.add(client.getTrainingSessionsPerWeek());
        params.add(client.getId());
        DatabaseManager.sendQuery(query, params);
    }

    public void deleteClient(Client client) throws SQLException {
        String query = "UPDATE clients set deleted = 1 WHERE id = ?";
        List<Object> params = new ArrayList<>();
        params.add(client.getId());
        DatabaseManager.sendQuery(query, params);
    }
}
