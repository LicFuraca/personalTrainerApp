package model;

import java.time.Month;

public class Message {
    private String clientName;
    private int monthPrice;
    private int monthSessions;
    private Month month;

    public Message(String clientName, int monthPrice, int monthSessions, Month month) {
        this.clientName = clientName;
        this.monthPrice = monthPrice;
        this.monthSessions = monthSessions;
        this.month = month;
    }

    private String formatMonth() {
        String monthTranslated = "";

        switch (month) {
            case JANUARY:
                monthTranslated = "enero";
                break;
            case FEBRUARY:
                monthTranslated = "febrero";
                break;
            case MARCH:
                monthTranslated = "marzo";
                break;
            case APRIL:
                monthTranslated = "abril";
                break;
            case MAY:
                monthTranslated = "mayo";
                break;
            case JUNE:
                monthTranslated = "junio";
                break;
            case JULY:
                monthTranslated = "julio";
                break;
            case AUGUST:
                monthTranslated = "agosto";
                break;
            case SEPTEMBER:
                monthTranslated = "septiembre";
                break;
            case OCTOBER:
                monthTranslated = "octubre";
                break;
            case NOVEMBER:
                monthTranslated = "noviembre";
                break;
            case DECEMBER:
                monthTranslated = "diciembre";
                break;
            default:
                monthTranslated = "";
                break;
        }

        return monthTranslated;
    }

    @Override
    public String toString() {
        String formatedMonth = formatMonth();

        return "Hola " + clientName + "! Te paso el mes de " + formatedMonth + " vamos a tener " + monthSessions
                + " clases este mes. " +
                " El valor de del mes es de $" + monthPrice;
    }
}
