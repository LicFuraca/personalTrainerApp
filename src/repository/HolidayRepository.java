package repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Holiday;
import model.TypeOfHoliday;
import util.DatabaseManager;

public class HolidayRepository {

    public List<Holiday> getAllHolidays() throws SQLException {
        String query = "SELECT * FROM holidays WHERE deleted != 1";
        ArrayList<ArrayList<String>> res = DatabaseManager.sendQuery(query);

        return mapRawDataToHolidays(res);
    }

    public List<Holiday> getHolidayForMonth(int month, int year) throws SQLException {
        String query = "SELECT * FROM holidays WHERE MONTH(startDate) = ? AND YEAR(endDate) = ? AND deleted != 1";

        List<Object> params = new ArrayList<>();
        params.add(month);
        params.add(year);
        ArrayList<ArrayList<String>> res = DatabaseManager.sendQuery(query, params);

        List<Holiday> holidays = mapRawDataToHolidays(res);

        return holidays;
    }

    public List<Holiday> getTrainerHolidays(int trainerId) throws SQLException {
        String query = "SELECT * FROM holidays WHERE id = ? AND deleted != 1";
        ArrayList<ArrayList<String>> res = DatabaseManager.sendQuery(query, trainerId);
        List<Holiday> holidays = mapRawDataToHolidays(res);

        return holidays;
    }

    public List<Holiday> getClientHolidays(int idClient) throws SQLException {
        String query = "SELECT * FROM holidays WHERE id = ? AND deleted != 1";
        ArrayList<ArrayList<String>> res = DatabaseManager.sendQuery(query, idClient);
        List<Holiday> holidays = mapRawDataToHolidays(res);

        return holidays;
    }

    private List<Holiday> mapRawDataToHolidays(ArrayList<ArrayList<String>> rawData) {
        List<Holiday> holidays = new ArrayList<>();

        for (ArrayList<String> row : rawData) {
            Holiday holiday = new Holiday(
                    Integer.parseInt(row.get(0)),
                    Integer.parseInt(row.get(1)),
                    LocalDate.parse(row.get(2)),
                    LocalDate.parse(row.get(3)),
                    row.get(4),
                    TypeOfHoliday.valueOf(row.get(5)));

            holidays.add(holiday);
        }

        return holidays;
    }

    public void saveHoliday(int idClient, LocalDate startDate, LocalDate endDate, String description,
            TypeOfHoliday type) throws SQLException {

        String query = "INSERT INTO holidays (idClient, startDate, endDate, description, type) VALUES (?, ?, ?, ?, ?)";

        List<Object> params = new ArrayList<>();
        params.add(idClient);
        params.add(startDate);
        params.add(endDate);
        params.add(description);
        params.add(type.toString());

        DatabaseManager.sendQuery(query, params);
    }

    public void updateHoliday(Holiday holiday) throws SQLException {
        String query = "UPDATE holidays set idClient = ?, startDate = ?, endDate = ?, description = ?, type = ? WHERE id = ?";

        List<Object> params = new ArrayList<>();
        params.add(holiday.getIdClient());
        params.add(holiday.getStartDate());
        params.add(holiday.getEndDate());
        params.add(holiday.getDescription());
        params.add(holiday.getType().toString());
        params.add(holiday.getId());

        DatabaseManager.sendQuery(query, params);
    }

    public void deleteHoliday(int id) throws SQLException {
        String query = "UPDATE holidays set deleted = 1 WHERE id = ?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        DatabaseManager.sendQuery(query, params);
    }
}
