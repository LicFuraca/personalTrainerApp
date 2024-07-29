package model;

public class Client {
    private int id;
    private String name;
    private String lastname;
    private String cellphone;
    private int trainingSessionsPerWeek;

    public Client(int id, String name, String lastname, String cellphone, int trainingSessionsPerWeek) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.cellphone = cellphone;
        this.trainingSessionsPerWeek = trainingSessionsPerWeek;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCellphone() {
        return cellphone;
    }

    public int getTrainingSessionsPerWeek() {
        return trainingSessionsPerWeek;
    }

    @Override
    public String toString() {
        return "Id: " + id + "\nName: " + name + "\nLastname: " + lastname + "\nCellphone: " + cellphone
                + "\nTraining sessions per week: " + trainingSessionsPerWeek + "\n";
    }
}
