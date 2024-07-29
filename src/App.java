import java.util.List;
import java.util.Scanner;

import model.Client;
import model.Message;
import model.TrainingSession;
import service.ClientService;
import service.HolidayService;
import service.TrainingSessionService;

public class App {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        ClientService clientService = new ClientService();
        List<Client> clients = clientService.getAllClients();

        HolidayService holidayService = new HolidayService();
        holidayService.askForHolidays(scanner, clientService);

        TrainingSessionService trainingService = new TrainingSessionService();
        trainingService.calculateTrainingSessionsForClient(6, holidayService);

        // Luego de agregarlas calcular monthSessions. (ahí se restan las sesiones de
        // vacaciones o feriados)
        // Calcular precio de cada sesión, para cada cliente.

        // Generar mensajes.

        // for (Client client : clients) {
        // List<TrainingSession> monthSessions =
        // clientService.calculateTrainingSessions(client.getId());
        // // int monthPrice = calcService.calculateClientMonthPrice(monthSessions);
        // // Message message = calcService.generatePaymentMessage();
        // System.out.println(monthSessions);
        // }

        scanner.close();
    }
}
