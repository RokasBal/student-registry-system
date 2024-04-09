package com.registry.studentregistrysystem;

import StudentData.Group;
import StudentData.Student;
import TableViews.MainTable;
import TableViews.ManagerStudentTable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
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
    @FXML
    private Label currentGroupLabel;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label studentIdLabel;
    @FXML
    private Button deleteEntryButton;
    @FXML
    private Button removeFromListButton;
    @FXML
    private ChoiceBox<String> choiceGroupSelection;
    @FXML
    private Button moveToOtherGroupButton;
    @FXML
    private DatePicker markAttendancePicker;

    Student rowData;

    private Controller w1Controller;

    public ManagerController(Controller w1Controller) {
        this.w1Controller = w1Controller;
    }

    public void addStudentToGroup() {
        Student student = new Student(firstNameInput.getText(), lastNameInput.getText(), studentIdInput.getText());
        Group group = getCurrentGroup();
        group.addStudent(student);
        tableManagerList.setItems(group.getStudentsForTable());
        tableManagerList.refresh();
        w1Controller.addStudentToTable();
        firstNameInput.setText("");
        lastNameInput.setText("");
        studentIdInput.setText("");
        System.out.println("Test " + group.groupStudents.getFirst().firstName + " " + group.groupStudents.getFirst().lastName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ManagerStudentTable table = new ManagerStudentTable(w1Controller);
        TableView<Student> tableView = table.createTableView();
        tableManagerList.getColumns().setAll(tableView.getColumns());
        tableManagerList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableManagerList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                if (newValue != null) {
                    rowData = newValue;
                    refreshSelection();
                }
            }
        });
        if (getCurrentGroup().groupId == -2) currentGroupLabel.setText("Current group: all students");
        else if (getCurrentGroup().groupId == -1) currentGroupLabel.setText("Current group: ungrouped students");
        else currentGroupLabel.setText("Current group: " + (getCurrentGroup().groupId + 1));

        markAttendancePicker.setOnAction(this::markAttendance);
        LocalDate minDate = LocalDate.of(2023, 1, 1);
        LocalDate maxDate = LocalDate.of(2024, 12, 31);
        markAttendancePicker.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                    }});
    }

    public void remakeTable() {
        ManagerStudentTable table = new ManagerStudentTable(w1Controller);
        TableView<Student> tableView = table.createTableView();
        tableManagerList.getColumns().setAll(tableView.getColumns());
//        tableManagerList.setItems(getCurrentGroup().getStudentsForTable());
        System.out.println("Attempting to remake table");
    }

    public void refreshTable() {
        tableManagerList.getItems().removeAll();
        tableManagerList.setItems(getCurrentGroup().getStudentsForTable());
        if (getCurrentGroup().groupId == -2) currentGroupLabel.setText("Current group: all students");
        else if (getCurrentGroup().groupId == -1) currentGroupLabel.setText("Current group: ungrouped students");
        else currentGroupLabel.setText("Current group: " + (getCurrentGroup().groupId + 1));
        clearStudentSelection();
    }

    public void refreshSelection() {
        firstNameLabel.setText(rowData.firstName.getValue());
        lastNameLabel.setText(rowData.lastName.getValue());
        studentIdLabel.setText(rowData.studentId.getValue());
    }

    public void clearStudentSelection() {
        firstNameLabel.setText("");
        lastNameLabel.setText("");
        studentIdLabel.setText("");
        rowData = null;
    }

    public void markAttendance(ActionEvent actionEvent) {
        if (rowData == null) markAttendancePicker.setValue(null);
        else {
            if (Objects.equals(rowData.getAttendance(markAttendancePicker.getValue().getYear(), markAttendancePicker.getValue().getMonthValue(), markAttendancePicker.getValue().getDayOfMonth()), "x")) {
                rowData.markAttendance(markAttendancePicker.getValue().getYear(), markAttendancePicker.getValue().getMonthValue(), markAttendancePicker.getValue().getDayOfMonth(), null);
                w1Controller.remakeTable();
            } else {
                rowData.markAttendance(markAttendancePicker.getValue().getYear(), markAttendancePicker.getValue().getMonthValue(), markAttendancePicker.getValue().getDayOfMonth(), "x");
                w1Controller.remakeTable();
            }
        }
        System.out.println("Date selected");
        System.out.println("Attendance array: " + rowData.getAttendance(markAttendancePicker.getValue().getYear(), markAttendancePicker.getValue().getMonthValue(), markAttendancePicker.getValue().getDayOfMonth()));
    }
}
