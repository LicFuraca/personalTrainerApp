package model;

import java.time.DayOfWeek;

import util.DateUtil;

public class TrainingSessionRelations {
    private int id;
    private int clientId;
    private int trainingSessionDetailsId;
    private DayOfWeek day;

    public TrainingSessionRelations(int id, int clientId, int trainingSessionDetailsId, String day) {
        this.id = id;
        this.clientId = clientId;
        this.trainingSessionDetailsId = trainingSessionDetailsId;
        setDay(day);
    }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public int getTrainingSessionDetailsId() {
        return trainingSessionDetailsId;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(String dayString) {
        this.day = DateUtil.formatDay(dayString);
    }
}
