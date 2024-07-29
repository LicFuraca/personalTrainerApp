package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

public class DateUtil {

    public static int countSpecificDays(DayOfWeek day) {
        LocalDate today = LocalDate.now();
        Month month = today.getMonth();
        int year = today.getYear();
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        int dayCount = 0;
        for (int i = 1; i <= daysInMonth; i++) {
            LocalDate date = LocalDate.of(year, month, i);

            if (date.getDayOfWeek() == day) {
                dayCount++;
            }
        }

        return dayCount;
    }

    public static DayOfWeek formatDay(String day) {
        day = day.toLowerCase();
        DayOfWeek res;

        switch (day) {
            case "lunes":
                res = DayOfWeek.MONDAY;
                break;
            case "martes":
                res = DayOfWeek.TUESDAY;
                break;
            case "miercoles":
                res = DayOfWeek.WEDNESDAY;
                break;
            case "jueves":
                res = DayOfWeek.THURSDAY;
                break;
            case "viernes":
                res = DayOfWeek.FRIDAY;
                break;

            default:
                res = null;
        }

        return res;
    }
}
