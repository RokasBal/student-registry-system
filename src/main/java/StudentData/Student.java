package StudentData;

import Utility.GetDisplayDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.*;

import static Settings.Restrictions.END_YEAR;
import static Settings.Restrictions.START_YEAR;

public class Student {
    public StringProperty firstName;
    public StringProperty lastName;

    public StringProperty studentId;

    public List<List<List<String>>> attendance;

    public Student(String firstName, String lastName, String studentId) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.studentId = new SimpleStringProperty(studentId);
        attendance = new ArrayList<>();
    }


    public void markAttendance(int year, int month, int day, String value) {
        while (attendance.size() <= year - 2023) {
            attendance.add(new ArrayList<>());
        }

        List<List<String>> yearAttendance = attendance.get(year - 2023);

        while (yearAttendance.size() < month) {
            yearAttendance.add(new ArrayList<>());
        }

        List<String> monthAttendance = yearAttendance.get(month - 1);

        while (monthAttendance.size() < day) {
            monthAttendance.add(null);
        }

        monthAttendance.set(day - 1, value);
    }

    public String getAttendance(int year, int month, int day) {
        if (year - 2023 < 0 || year - 2023 >= attendance.size()) {
            return null; // Year is out of range
        }

        List<List<String>> yearAttendance = attendance.get(year - 2023);

        if (month < 1 || month > yearAttendance.size()) {
            return null; // Month is out of range
        }

        List<String> monthAttendance = yearAttendance.get(month - 1);

        if (day < 1 || day > monthAttendance.size()) {
            return null; // Day is out of range
        }

        return monthAttendance.get(day - 1);
    }

    public ArrayList<String> getAttendanceExport() {
        ArrayList<String> export = new ArrayList<>();

        for (int j = START_YEAR; j <= END_YEAR; j++) {
            for (int k = 0; k < 12; k++) {
                for (int i = 0; i < GetDisplayDate.getMonthLengthForExport(j, k + 1); i++) {
                    if (j - START_YEAR < 0 || j - START_YEAR >= attendance.size()) continue;
                    if (k + 1 < 1 || k + 1 > attendance.get(j - START_YEAR).size()) continue;
                    if (i + 1 < 1 || i + 1 > attendance.get(j - START_YEAR).get(k).size()) continue;

                    if (Objects.equals(attendance.get(j - START_YEAR).get(k).get(i), "x")) {
                        if (k < 10) {
                            if (i < 9) export.add(j + "-" + "0" + (k + 1) + "-0"+ (i + 1) + ", ");
                            else export.add(j + "-" + "0" + (k + 1) + "-" + (i + 1) + ", ");
                        } else {
                            if (i < 9) export.add(j + "-" + (k + 1) + "-0"+ (i + 1) + ", ");
                            else export.add(j + "-" + (k + 1) + "-" + (i + 1) + ", ");
                        }
                    }
                }
            }
        }

        return export;
    }

    public StringProperty getFirstName() {
        return firstName;
    }

    public StringProperty getLastName() {
        return lastName;
    }
    public StringProperty getStudentId() { return studentId; }

//    public StringProperty[] getAttendace() {
//        return attendace;
//    }
}
