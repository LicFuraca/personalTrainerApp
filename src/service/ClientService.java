package service;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import model.Client;
import model.Holiday;
import model.TrainingSession;
import model.TrainingSessionDetails;
import model.TrainingSessionRelations;
import model.TrainingSessionType;
import repository.ClientRepository;
import repository.TrainingSessionDetailsRepository;
import repository.TrainingSessionRelationsRepository;

public class ClientService {
    private final int NAME = 0;
    private final int LASTNAME = 1;

    public List<Client> getAllClients() {
        List<Client> allClients = null;

        try {
            ClientRepository clientRepo = new ClientRepository();
            allClients = clientRepo.getAllClients();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allClients;
    }

    public int getClientIdWithFullName(Scanner scanner) {
        String fullName;
        int idClient;

        do {
            System.out.println(
                    "Ingresa el nombre completo del cliente con un espacio entre nombre y apellido, no puede estar vacío.");
            fullName = scanner.nextLine();
        } while (fullName.isEmpty());

        idClient = generateClientId(fullName);

        return idClient;
    }

    private int generateClientId(String fullName) {
        List<String> nameParts = List.of(fullName.split(" "));

        try {
            ClientRepository clientRepo = new ClientRepository();
            int clientId = clientRepo.getClientIdByName(nameParts.get(NAME), nameParts.get(LASTNAME));
            return clientId;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<TrainingSession> calculateTrainingSessions(int idClient) {
        List<TrainingSession> monthSessions = new ArrayList<>();

        try {
            ClientRepository clientRepo = new ClientRepository();
            Client client = clientRepo.getClientById(idClient);

            if (client == null) {
                return monthSessions;
            }

            TrainingSessionRelationsRepository trainingRelationsRepo = new TrainingSessionRelationsRepository();
            List<TrainingSessionRelations> trainingRelations = trainingRelationsRepo
                    .getTrainingSessionRelationsByClient(idClient);

            if (trainingRelations.isEmpty()) {
                return monthSessions;
            }

            TrainingSessionDetailsRepository trainingDetailsRepo = new TrainingSessionDetailsRepository();
            HolidayService holidayService = new HolidayService();
            List<Holiday> clientHolidays = holidayService.getClientHolidays(idClient);
            List<Holiday> trainerHolidays = holidayService.getTrainerHolidays();
            List<Holiday> monthHolidays = holidayService.getHolidaysForMonth();

            Set<Integer> detailsIds = trainingRelations
                    .stream()
                    .map(TrainingSessionRelations::getTrainingSessionDetailsId)
                    .collect(Collectors.toSet());

            List<TrainingSessionDetails> details = trainingDetailsRepo
                    .getTrainingSessionDetailsByIds(detailsIds);

            YearMonth currentMonth = YearMonth.now();
            int lastDayInMonth = currentMonth.lengthOfMonth();

            for (int day = 1; day <= lastDayInMonth; day++) {
                LocalDate date = currentMonth.atDay(day);
                DayOfWeek dayOfWeek = date.getDayOfWeek();

                if (holidayService.isHoliday(date, clientHolidays, trainerHolidays, monthHolidays)) {
                    continue;
                }

                for (TrainingSessionRelations relation : trainingRelations) {
                    DayOfWeek relationDay = relation.getDay();

                    if (relationDay == dayOfWeek) {
                        int idDetails = relation.getTrainingSessionDetailsId();
                        TrainingSessionDetails details = details.get(idDetails);

                        if (details != null) {
                            TrainingSession session = createTrainingSession(details.getPrice(), relationDay,
                                    details.getType());
                            monthSessions.add(session);
                            break; // Si solo se necesita una sesión por día
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return monthSessions;
    }

    private TrainingSession createTrainingSession(int price, DayOfWeek day, TrainingSessionType type) {
        return new TrainingSession(price, day, type);
    }

    public void listAllClients() {
        List<Client> clients = getAllClients();

        System.out.println("Lista de clientes: ");
        for (Client client : clients) {
            System.out.println(client.toString());
        }
    }
}
