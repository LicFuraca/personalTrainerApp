package repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.TrainingSessionDetails;
import model.TrainingSessionType;
import util.DatabaseManager;

public class TrainingSessionDetailsRepository {

    public List<TrainingSessionDetails> getAllTrainingSessionsDetails() throws SQLException {
        String query = "SELECT * FROM trainingSessionDetails";
        ArrayList<ArrayList<String>> res = DatabaseManager.sendQuery(query);
        List<TrainingSessionDetails> trainingSessionDetails = mapRawDataToTrainingSessionDetails(res);

        return trainingSessionDetails;
    }

    public Map<Integer, TrainingSessionDetails> getTrainingSessionDetailsByIds(Set<Integer> detailsIds)
            throws SQLException {
        Map<Integer, TrainingSessionDetails> detailsMap = new HashMap<>();

        if (detailsIds.isEmpty()) {
            return detailsMap;
        }

        StringBuilder query = new StringBuilder("SELECT * FROM trainingSessionDetails WHERE id IN (");
        for (int i = 0; i < detailsIds.size(); i++) {
            query.append("?");
            if (i < detailsIds.size() - 1) {
                query.append(", ");
            }
        }

        query.append(")");

        List<Object> params = new ArrayList<>(detailsIds);
        ArrayList<ArrayList<String>> res = DatabaseManager.sendQuery(query.toString(), params);
        List<TrainingSessionDetails> detailsList = mapRawDataToTrainingSessionDetails(res);

        for (TrainingSessionDetails details : detailsList) {
            detailsMap.put(details.getId(), details);
        }

        return detailsMap;
    }

    public TrainingSessionDetails getTrainingSessionDetailsById(int id) throws SQLException {
        String query = "SELECT * FROM trainingSessionDetails WHERE id = ?";

        ArrayList<ArrayList<String>> res = DatabaseManager.sendQuery(query, id);
        List<TrainingSessionDetails> trainingSessionDetails = mapRawDataToTrainingSessionDetails(res);

        return trainingSessionDetails.isEmpty() ? null : trainingSessionDetails.get(0);
    }

    private List<TrainingSessionDetails> mapRawDataToTrainingSessionDetails(ArrayList<ArrayList<String>> res) {
        List<TrainingSessionDetails> listTrainingSessionDetails = new ArrayList<>();

        for (ArrayList<String> row : res) {
            TrainingSessionDetails trainingSessionDetails = new TrainingSessionDetails(
                    Integer.parseInt(row.get(0)),
                    Integer.parseInt(row.get(1)),
                    TrainingSessionType.valueOf(row.get(2)));
            listTrainingSessionDetails.add(trainingSessionDetails);
        }

        return listTrainingSessionDetails;
    }

    public void saveTrainingSessionDetails(TrainingSessionDetails trainingSessionDetails) throws SQLException {
        String query = "INSERT INTO trainingSessionDetails (id, price, type) VALUES (?, ?, ?)";
        List<Object> params = new ArrayList<>();
        params.add(trainingSessionDetails.getId());
        params.add(trainingSessionDetails.getPrice());
        params.add(trainingSessionDetails.getType());
        DatabaseManager.sendQuery(query, params);
    }

    public void updateTrainingSessionDetails(TrainingSessionDetails trainingSessionDetails) throws SQLException {
        String query = "UPDATE trainingSessionDetails SET price = ?, type = ? WHERE id = ?";
        List<Object> params = new ArrayList<>();
        params.add(trainingSessionDetails.getPrice());
        params.add(trainingSessionDetails.getType());
        params.add(trainingSessionDetails.getId());
        DatabaseManager.sendQuery(query, params);
    }

    public void deleteTrainingSessionDetails(TrainingSessionDetails trainingSessionDetails) throws SQLException {
        String query = "UPDATE trainingSessionDetails SET borrado = 1 WHERE id = ?";
        List<Object> params = new ArrayList<>();
        params.add(trainingSessionDetails.getId());
        DatabaseManager.sendQuery(query, params);
    }
}
