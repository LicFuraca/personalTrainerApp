package model;

import java.time.LocalDate;

public class Holiday {
    public static final int TRAINER_ID = 0;
    public static final int MONTH_HOLIDAY_ID = -1;

    private int id;
    private int idClient;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private TypeOfHoliday type;

    public Holiday(int id, int idClient, LocalDate startDate, LocalDate endDate, String description,
            TypeOfHoliday type) {

        this.id = id;
        this.idClient = idClient;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getIdClient() {
        return idClient;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public TypeOfHoliday getType() {
        return type;
    }

    public boolean isDateWithinRange(LocalDate date) {
        return (date.isEqual(startDate) || date.isAfter(startDate) && date.isEqual(endDate) || date.isBefore(endDate));
    }
}
