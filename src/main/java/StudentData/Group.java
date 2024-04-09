package StudentData;

import com.registry.studentregistrysystem.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Group {
    public int groupId;
    public ArrayList<Student> groupStudents;
    public ObservableList<Student> dataList = FXCollections.observableArrayList();

    public Group(int groupId) {
        this.groupId = groupId;
        groupStudents = new ArrayList<>();
    }

    public void addStudent(Student student) {
        groupStudents.add(student);
        dataList.add(student);
    }

    public ObservableList<Student> getStudentsForTable() {
        return dataList;
    }

    public void removeStudent(Student student) {
        groupStudents.remove(student);
        dataList.remove(student);
        if (groupStudents.isEmpty()) dataList.clear();
    }
}
