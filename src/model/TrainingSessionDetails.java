package model;

public class TrainingSessionDetails {
    private int id;
    private int price;
    private TrainingSessionType type;

    public TrainingSessionDetails(int id, int price, TrainingSessionType type) {
        this.id = id;
        this.price = price;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public TrainingSessionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Id: " + id + "\nPrice: " + price + "\nType: " + type;
    }
}
