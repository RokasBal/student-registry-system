package StudentData;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student {
    public StringProperty firstName;
    public StringProperty lastName;

    public StringProperty studentId;

    private List<List<List<String>>> attendance;

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
