package TableViews;

import StudentData.Student;
import Utility.GetDisplayDate;
import com.registry.studentregistrysystem.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

import static com.registry.studentregistrysystem.Controller.getCurrentGroup;

public class MainTable extends AbsTableView<Student> {

    public int[] monthLenghts = new int[12];

    public Controller controller;

    public MainTable(Controller controller) {
        this.controller = controller;
    }

    @Override
    public TableView<Student> createTableView() {
        GetDisplayDate getDisplayDate = new GetDisplayDate(controller);

        TableView<Student> tableView = new TableView<>();

        TableColumn<Student, String> firstNameColumn = new TableColumn<>("First name");
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstName());
        firstNameColumn.setPrefWidth(125);

        TableColumn<Student, String> lastNameColumn = new TableColumn<>("Last name");
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastName());
        lastNameColumn.setPrefWidth(125);

        tableView.getColumns().addAll(firstNameColumn, lastNameColumn);

        for (int i = 1; i <= GetDisplayDate.getMonthLength(); i++) {
            if (controller.attendanceToggle) {
                boolean attendanceFound = false;
                for (Student student : getCurrentGroup().groupStudents) {
                    if (Objects.equals(student.getAttendance(controller.getYear(), GetDisplayDate.getMonthIndex(), i), "x")) {
                        attendanceFound = true;
                        break;
                    }
                }
                if (!attendanceFound) continue;
            }

            TableColumn<Student, String> column = new TableColumn<>(String.valueOf(i));
            column.setPrefWidth(30);
            int day = i;
            column.setCellValueFactory(data -> {
                StringProperty property = new SimpleStringProperty();
                String attendanceValue = data.getValue().getAttendance(controller.getYear(), GetDisplayDate.getMonthIndex(), day);
                // Handle null attendance values
                if (attendanceValue == null) {
                    property.setValue(""); // or set any other representation for absence
                } else {
                    property.setValue(attendanceValue);
                }
                return property;
            });
            tableView.getColumns().add(column);
        }

        return tableView;
    }
}
