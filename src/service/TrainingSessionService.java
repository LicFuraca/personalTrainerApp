package service;

import model.TrainingSession;
import model.TrainingSessionDetails;
import model.TrainingSessionRelations;
import repository.TrainingSessionDetailsRepository;
import repository.TrainingSessionRelationsRepository;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TrainingSessionService {

    public List<TrainingSession> calculateTrainingSessionsForClient(int idClient, HolidayService holidayService) {
        List<TrainingSession> sessions = new ArrayList<>();
        List<TrainingSessionRelations> relations = getTrainingSessionRelationsByClient(idClient);
        Set<Integer> detailsIds = relations.stream()
                .map(TrainingSessionRelations::getTrainingSessionDetailsId)
                .collect(Collectors.toSet());

        Map<Integer, TrainingSessionDetails> detailsMap = getTrainingSessionDetailsByIds(detailsIds);
        sessions = generateSessions(detailsMap, relations);

        return sessions;
    }

    private List<TrainingSessionRelations> getTrainingSessionRelationsByClient(int idClient) {
        List<TrainingSessionRelations> relations = new ArrayList<>();

        try {
            TrainingSessionRelationsRepository relationsRepo = new TrainingSessionRelationsRepository();
            relations = relationsRepo.getTrainingSessionRelationsByClient(idClient);
        } catch (SQLException e) {
            System.out.println("Error al buscar los detalles de la sesión: " + e.getMessage());
            e.printStackTrace();
        }

        return relations;
    }

    private Map<Integer, TrainingSessionDetails> getTrainingSessionDetailsByIds(Set<Integer> detailsIds) {
        Map<Integer, TrainingSessionDetails> detailsMap = new HashMap<>();

        try {
            TrainingSessionDetailsRepository detailsRepo = new TrainingSessionDetailsRepository();
            detailsMap = detailsRepo.getTrainingSessionDetailsByIds(detailsIds);
        } catch (SQLException e) {
            System.out.println("Error al buscar los detalles de la sesión: " + e.getMessage());
            e.printStackTrace();
        }

        return detailsMap;
    }

    private List<TrainingSession> generateSessions(Map<Integer, TrainingSessionDetails> detailsMap,
            List<TrainingSessionRelations> relations) {

        List<TrainingSession> sessions = new ArrayList<>();

        for (TrainingSessionRelations relation : relations) {
            int detailId = relation.getTrainingSessionDetailsId();

            if (detailsMap.containsKey(detailId)) {
                TrainingSessionDetails details = detailsMap.get(detailId);
                TrainingSession session = new TrainingSession(
                        details.getPrice(),
                        relation.getDay(),
                        details.getType());

                sessions.add(session);
            }
        }

        return sessions;
    }
}
