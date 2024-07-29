package repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.TrainingSessionRelations;
import util.DatabaseManager;

public class TrainingSessionRelationsRepository {

    public List<TrainingSessionRelations> getTrainingSessionRelationsByClient(int idClient) throws SQLException {
        String query = "SELECT * FROM trainingSessionRelations WHERE idClient = ?";
        ArrayList<ArrayList<String>> res = DatabaseManager.sendQuery(query, idClient);
        List<TrainingSessionRelations> trainingSessionRelations = mapRawDataToTrainingSessionRelations(res);

        return trainingSessionRelations.isEmpty() ? null : trainingSessionRelations;
    }

    public ArrayList<String> getWeeklyTrainingDays(int idClient) throws SQLException {
        String query = "SELECT day FROM trainingSessionRelations WHERE idClient = ?";
        ArrayList<ArrayList<String>> res = DatabaseManager.sendQuery(query, idClient);
        ArrayList<String> daysOfWeek = new ArrayList<>();

        for (ArrayList<String> row : res) {
            daysOfWeek.add(row.get(3));
        }

        return daysOfWeek;
    }

    public ArrayList<Integer> getIdTrainingSessionDetailsByClient(int idClient) throws SQLException {
        ArrayList<Integer> idsTrainignSessionDetails = new ArrayList<>();
        String query = "SELECT idTrainingSessionDetails FROM trainingSessionRelations WHERE idClient = ?";
        ArrayList<ArrayList<String>> res = DatabaseManager.sendQuery(query, idClient);

        for (ArrayList<String> row : res) {
            idsTrainignSessionDetails.add(Integer.parseInt(row.get(2)));
        }

        return idsTrainignSessionDetails;
    }

    private List<TrainingSessionRelations> mapRawDataToTrainingSessionRelations(ArrayList<ArrayList<String>> rawData) {
        List<TrainingSessionRelations> trainingSessionRelations = new ArrayList<>();
        for (ArrayList<String> row : rawData) {
            trainingSessionRelations.add(new TrainingSessionRelations(
                    Integer.parseInt(row.get(0)),
                    Integer.parseInt(row.get(1)),
                    Integer.parseInt(row.get(2)),
                    (row.get(3))));
        }

        return trainingSessionRelations;
    }

    public void saveTrainingSessionRelation(TrainingSessionRelations trainingSessionRelations) throws SQLException {
        String query = "INSERT INTO trainingSessionRelations (clientId, trainingSessionDetailsId, day) VALUES (?, ?, ?)";
        List<Object> params = new ArrayList<>();
        params.add(trainingSessionRelations.getClientId());
        params.add(trainingSessionRelations.getTrainingSessionDetailsId());
        params.add(trainingSessionRelations.getDay());
        DatabaseManager.sendQuery(query, params);
    }

    public void updateTrainingSessionRelation(TrainingSessionRelations trainingSessionRelations) throws SQLException {
        String query = "UPDATE trainingSessionRelations SET clientId = ?, trainingSessionDetailsId = ?, day = ? WHERE id = ?";
        List<Object> params = new ArrayList<>();
        params.add(trainingSessionRelations.getClientId());
        params.add(trainingSessionRelations.getTrainingSessionDetailsId());
        params.add(trainingSessionRelations.getDay());
        params.add(trainingSessionRelations.getId());
        DatabaseManager.sendQuery(query, params);
    }

    public void deleteTrainingSessionRelation(TrainingSessionRelations trainingSessionRelations) throws SQLException {
        String query = "UPDATE trainingSessionRelations SET deleted = 1 WHERE id = ?";
        List<Object> params = new ArrayList<>();
        params.add(trainingSessionRelations.getId());
        DatabaseManager.sendQuery(query, params);
    }
}
