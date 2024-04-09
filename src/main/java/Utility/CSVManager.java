package Utility;

import StudentData.Group;
import StudentData.Student;
import javafx.collections.ObservableList;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVManager {
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
}
