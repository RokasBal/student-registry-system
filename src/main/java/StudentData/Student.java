package StudentData;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    public StringProperty firstName;
    public StringProperty lastName;

    public StringProperty studentId;

    public StringProperty[] attendace;

    public Student(String firstName, String lastName, String studentId) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.studentId = new SimpleStringProperty(studentId);
    }

    public StringProperty getFirstName() {
        return firstName;
    }

    public StringProperty getLastName() {
        return lastName;
    }
    public StringProperty getStudentId() { return studentId; }

    public StringProperty[] getAttendace() {
        return attendace;
    }
}
