package service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import repository.HolidayRepository;
import model.Holiday;
import model.TypeOfHoliday;

public class HolidayService {
    private final int DATES_INPUT_LIMIT = 2;

    public List<Holiday> getClientHolidays(int clientId) {
        List<Holiday> clientHolidays = null;

        try {
            HolidayRepository holidayRepo = new HolidayRepository();
            clientHolidays = holidayRepo.getClientHolidays(clientId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientHolidays;
    }

    public List<Holiday> getTrainerHolidays() {
        List<Holiday> trainerHolidays = null;

        try {
            HolidayRepository holidayRepo = new HolidayRepository();
            trainerHolidays = holidayRepo.getTrainerHolidays(Holiday.TRAINER_ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trainerHolidays;
    }

    public List<Holiday> getHolidaysForMonth() {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int year = today.getYear();

        List<Holiday> monthHolidays = new ArrayList<>();

        try {
            HolidayRepository holidayRepo = new HolidayRepository();
            monthHolidays = holidayRepo.getHolidayForMonth(month, year);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return monthHolidays;
    }

    public boolean isHoliday(LocalDate date, List<Holiday> clientHolidays, List<Holiday> trainerHolidays,
            List<Holiday> monthHolidays) {

        return clientHolidays.stream().anyMatch(holiday -> holiday.isDateWithinRange(date)) ||
                trainerHolidays.stream().anyMatch(holiday -> holiday.isDateWithinRange(date)) ||
                monthHolidays.stream().anyMatch(holiday -> holiday.isDateWithinRange(date));
    }

    public void askForHolidays(Scanner scanner, ClientService clientService) {
        boolean monthWithHoliday = isThereAnyHoliday(scanner);

        while (monthWithHoliday) {
            if (monthWithHoliday) {
                TypeOfHoliday type = whatKindOfHolidayIs(scanner);

                if (type == TypeOfHoliday.HOLIDAY) {
                    List<LocalDate> dates = askForDates(scanner, true);

                    if (dates.size() > 0) {
                        addMonthHoliday(dates.get(0), dates.get(0), "Feriado",
                                TypeOfHoliday.HOLIDAY);

                        System.out.println("Feriado añadido correctamente");
                    }

                } else if (type == TypeOfHoliday.VACATION) {
                    boolean isClient = isClientHoliday(scanner);

                    if (isClient) {
                        int idClient = clientService.getClientIdWithFullName(scanner);
                        List<LocalDate> dates = askForDates(scanner, false);

                        if (dates.size() > 0) {
                            addClientHoliday(idClient, dates.get(0), dates.get(1), TypeOfHoliday.VACATION);
                            System.out.println("Vacaciones añadidas correctamente");
                        }
                    } else {
                        List<LocalDate> dates = askForDates(scanner, false);

                        if (dates.size() > 0) {
                            addTrainerHoliday(dates.get(0), dates.get(1), "Vacaciones",
                                    TypeOfHoliday.VACATION);

                            System.out.println("Vacaciones añadidas correctamente");
                        }
                    }
                }
            }

            monthWithHoliday = isThereAnyHoliday(scanner);
        }
    }

    private boolean isThereAnyHoliday(Scanner scanner) {
        String input;

        do {
            System.out.println("¿Hay vacaciones o feriados para este mes? S/N");
            input = scanner.nextLine().toLowerCase();
        } while (input.equals("s") && input.equals("n"));

        return input.equals("s");
    }

    private TypeOfHoliday whatKindOfHolidayIs(Scanner scanner) {
        String input;

        do {
            System.out.println("¿Feriado o vacaciones? F / V");
            input = scanner.nextLine().toLowerCase();
        } while (input.equals("f") && input.equals("v"));

        return input.equals("f") ? TypeOfHoliday.HOLIDAY : TypeOfHoliday.VACATION;
    }

    private boolean isClientHoliday(Scanner scanner) {
        String input;

        do {
            System.out.println("¿Es para un cliente? S/N");
            input = scanner.nextLine().toLowerCase();
        } while (input.equals("s") && input.equals("n"));

        return input.equals("s");
    }

    private List<LocalDate> askForDates(Scanner scanner, boolean isHoliday) {
        List<LocalDate> dates = new ArrayList<>();
        String input;

        String dateDetail = dates.size() == 1 ? "inicio" : "fin";

        do {
            System.out.println("Ingresa la fecha de " + dateDetail + " con el formato dd/mm/yy o 'F' para terminar.");
            input = scanner.nextLine().toLowerCase();

            if (input.equals("f")) {
                break;
            }

            LocalDate validatedDate = formatDate(input);
            dates.add(validatedDate);
        } while (input.equals("f") || dates.size() <= DATES_INPUT_LIMIT && !isHoliday);

        return dates;
    }

    private void addMonthHoliday(LocalDate startDate, LocalDate endDate, String description, TypeOfHoliday type) {
        try {
            HolidayRepository holidayRepo = new HolidayRepository();
            holidayRepo.saveHoliday(Holiday.MONTH_HOLIDAY_ID, startDate, endDate, description, type);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTrainerHoliday(LocalDate startDate, LocalDate endDate, String description, TypeOfHoliday type) {
        try {
            HolidayRepository holidayRepo = new HolidayRepository();
            holidayRepo.saveHoliday(Holiday.TRAINER_ID, startDate, endDate, description, type);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addClientHoliday(int idClient, LocalDate startDate, LocalDate endDate, TypeOfHoliday type) {
        String description = "Periodo de vacaciones";

        try {
            HolidayRepository holidayRepo = new HolidayRepository();
            holidayRepo.saveHoliday(idClient, startDate, endDate, description, type);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private LocalDate formatDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Formato inválido, vuelva a ejecutar el programa.");
            return null;
        }
    }

}
