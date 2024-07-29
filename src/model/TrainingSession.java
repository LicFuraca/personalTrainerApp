package model;

import java.time.DayOfWeek;

public class TrainingSession {
    private int price;
    private DayOfWeek day;
    private TrainingSessionType type;

    public TrainingSession(int price, DayOfWeek day, TrainingSessionType type) {
        this.price = price;
        this.day = day;
        this.type = type;
    }

    public int getPrice() {
        return this.price;
    }

    public DayOfWeek getDay() {
        return this.day;
    }

    public TrainingSessionType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "Precio: " + price + "\n" + " Día de semana: " + day + "\n" + " Tipo: " + type;
    }
}
