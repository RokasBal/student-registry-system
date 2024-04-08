package com.registry.studentregistrysystem;

import StudentData.Group;
import StudentData.Student;
import TableViews.MainTable;
import TableViews.ManagerStudentTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static com.registry.studentregistrysystem.Controller.getCurrentGroup;

public class ManagerController implements Initializable {
    @FXML
    private Button buttonAddStudent;
    @FXML
    private TextField firstNameInput;
    @FXML
    private TextField lastNameInput;
    @FXML
    private TextField studentIdInput;
    @FXML
    private TableView<Student> tableManagerList;

    private Controller w1Controller;

    public ManagerController(Controller w1Controller) {
        this.w1Controller = w1Controller;
    }
    public void addStudentToGroup() {
//        Controller w1Controller = Controller.getInstance();

        Student student = new Student(firstNameInput.getText(), lastNameInput.getText(), studentIdInput.getText());
        Group group = getCurrentGroup();
        group.addStudent(student);
        tableManagerList.setItems(group.getStudentsForTable());
        tableManagerList.refresh();
        w1Controller.addStudentToTable();
        System.out.println("Test " + group.groupStudents.getFirst().firstName + " " + group.groupStudents.getFirst().lastName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ManagerStudentTable table = new ManagerStudentTable(w1Controller);
        TableView<Student> tableView = table.createTableView();
        tableManagerList.getColumns().setAll(tableView.getColumns());
    }
}
