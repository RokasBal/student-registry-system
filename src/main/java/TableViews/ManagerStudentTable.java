package TableViews;

import StudentData.Student;
import Utility.GetDisplayDate;
import com.registry.studentregistrysystem.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ManagerStudentTable extends AbsTableView<Student> {

    private Controller controller;

    public ManagerStudentTable(Controller controller) {
        this.controller = controller;
    }

    @Override
    public TableView<Student> createTableView() {
        TableView<Student> tableView = new TableView<>();

        TableColumn<Student, String> firstNameColumn = new TableColumn<>("First name");
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstName());

        TableColumn<Student, String> lastNameColumn = new TableColumn<>("Last name");
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastName());

        TableColumn<Student, String> studentIdColumn = new TableColumn<>("Student ID");
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getStudentId());

        tableView.getColumns().addAll(firstNameColumn, lastNameColumn, studentIdColumn);

        return tableView;
    }
}
