package Utility;

import StudentData.Group;
import StudentData.Student;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.registry.studentregistrysystem.Controller;
import javafx.collections.ObservableList;

import com.opencsv.CSVWriter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CSVManager {
    private static Controller controller;

    public CSVManager(Controller controller) {
        this.controller = controller;
    }

    public static void saveToFile(ArrayList<Group> dataList, String filePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            String[] header = {"Group ID", "Student's First Name", "Student's Last Name", "Student ID", "Attendance"};
            writer.writeNext(header);

            for (Group data : dataList) {
                if(data.groupId == -2) continue;
                for (Student student : data.groupStudents) {
                    String[] row = {String.valueOf(data.groupId), student.firstName.getValue(), student.lastName.getValue(), student.studentId.getValue(), String.valueOf(student.getAttendanceExport())};
                    writer.writeNext(row);
                }
            }

            System.out.println("CSV file written successfully: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Group> importFromCSV(String filePath) throws FileNotFoundException {
        ArrayList<Group> groups = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextRecord;
            reader.readNext();
            controller.clearGroups();
            groups.add(new Group(-2));
            controller.addEntryToGroupChoiceBox("All students");
            controller.addEntryToGroupChoiceBox("Ungrouped students");
            int previousId = -2;
            while ((nextRecord = reader.readNext()) != null) {
                int groupId = Integer.parseInt(nextRecord[0]);
                String firstName = nextRecord[1];
                String lastName = nextRecord[2];
                String studentId = nextRecord[3];
                String attendace = nextRecord[4];
                if (previousId != groupId) {
                    int tempId = groupId - 1;
                    while (tempId != previousId) {
                        groups.add(new Group(tempId));
                        if (groupId != -1) controller.addEntryToGroupChoiceBox("Group " + (tempId + 1));
                        tempId--;
                        controller.increaseCounter();
                    }

                    groups.add(new Group(groupId));
                    if (groupId != -1) controller.addEntryToGroupChoiceBox("Group " + (groupId + 1));
                    previousId = groupId;
                    controller.increaseCounter();
                }
                Student student = new Student(firstName, lastName, studentId);
                List<int[]>  dates = parseDates(attendace);
                if (dates != null) {
                    for (int[] date : dates) {
                        student.markAttendance(date[0], date[1], date[2], "x");
                    }
                }
                groups.getLast().addStudent(student);
                groups.getFirst().addStudent(student);

                System.out.println(groups.getLast().groupId + " " + groups.getLast().groupStudents.getLast().firstName + " " +  groups.getLast().groupStudents.getLast().lastName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        controller.increaseCounter();
        return groups;
    }

    public static ArrayList<int[]> parseDates(String datesString) {
        ArrayList<int[]> dates = new ArrayList<>();

        if (Objects.equals(datesString, "[]")) return null;

        datesString = datesString.substring(1, datesString.length() - 2).trim();

        String[] dateStrings = datesString.split(", ");

        for (String dateString : dateStrings) {
            if (!dateString.isEmpty()) {
                // Remove leading and trailing spaces and split by hyphen
                String[] parts = dateString.trim().split("-");
                // Convert parts to integers
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2].replaceAll("[^0-9]", ""));
                dates.add(new int[]{year, month, day});
            }
        }

        return dates;
    }
}
