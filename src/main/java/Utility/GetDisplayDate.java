package Utility;

import com.registry.studentregistrysystem.Controller;

import java.time.YearMonth;

public class GetDisplayDate {

    private static Controller controller;

    public GetDisplayDate(Controller controller) {
        this.controller = controller;
    }

    public static int getMonthLength() {
        if (controller.getMonth() == null) {
            // Handle the case where the month string is null
            return 0; // or throw an exception, depending on your requirements
        }

        int month = switch (controller.getMonth()) {
            case "January" -> 1;
            case "February" -> 2;
            case "March" -> 3;
            case "April" -> 4;
            case "May" -> 5;
            case "June" -> 6;
            case "July" -> 7;
            case "August" -> 8;
            case "September" -> 9;
            case "October" -> 10;
            case "November" -> 11;
            case "December" -> 12;
            default -> 0;
        };

        YearMonth yearMonth = YearMonth.of(controller.getYear(), month);
        return yearMonth.lengthOfMonth();
    }

    public static int getMonthLengthForExport(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.lengthOfMonth();
    }

    public static int getMonthIndex() {
        int month = switch (controller.getMonth()) {
            case "January" -> 1;
            case "February" -> 2;
            case "March" -> 3;
            case "April" -> 4;
            case "May" -> 5;
            case "June" -> 6;
            case "July" -> 7;
            case "August" -> 8;
            case "September" -> 9;
            case "October" -> 10;
            case "November" -> 11;
            case "December" -> 12;
            default -> 0;
        };

        return month;
    }

}
