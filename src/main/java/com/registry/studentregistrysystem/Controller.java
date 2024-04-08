package com.registry.studentregistrysystem;

import StudentData.Group;
import StudentData.Student;
import TableViews.MainTable;
import TableViews.ManagerStudentTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public static Controller instance;

    private Integer[] years = {2023, 2024};
    private String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
//    private Month month = LocalDate.now().getMonth();
    private boolean firstLoadComplete = false;

    private String month = "April";
    @FXML
    private ChoiceBox<String> boxStudentSelection;
    @FXML
    private ChoiceBox<Integer> choiceYear;
    @FXML
    private ChoiceBox<String> choiceMonth;
    @FXML
    private Label labelGroupSelection;
    @FXML
    private TableView<Student> tableStudentList;
    public static Group currentGroup;
    public ArrayList<Group> groups = new ArrayList<>();
    public int counter = 0;

    public Controller() {}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeNewGroup();
        makeNewGroup();
        labelGroupSelection.setText("Current group: ungrouped");

        MainTable mainTable = new MainTable(this);
        TableView<Student> tableView = mainTable.createTableView();
        tableStudentList.getColumns().setAll(tableView.getColumns());

        choiceYear.getItems().addAll(years);
        choiceYear.setOnAction(this::uponYearSelection);
        choiceMonth.getItems().addAll(months);
        choiceMonth.setOnAction(this::uponMonthSelection);

        firstLoadComplete = true;
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void openStudentManager() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("manageStudents.fxml"));
        ManagerController managerController = new ManagerController(this);
        fxmlLoader.setController(managerController);
        Scene manager = new Scene(fxmlLoader.load(), 1225, 813);
        Stage stage = new Stage();

        stage.setScene(manager);
        stage.show();
    }

    public void makeNewGroup() {
        Group group = new Group(counter - 2);
        groups.add(group);
        currentGroup = group;
        counter++;
        if(currentGroup.groupId == -2) boxStudentSelection.getItems().add("All students");
        else if(currentGroup.groupId == -1) boxStudentSelection.getItems().add("Ungrouped students");
        else {
            boxStudentSelection.getItems().add("Group " + counter);
            boxStudentSelection.setValue("Group " + counter);
        }
        System.out.println("Success");
    }

    public void switchGroup(ActionEvent actionEvent) {
        currentGroup = groups.get(Integer.parseInt(boxStudentSelection.getValue()));
        labelGroupSelection.setText("Current group: " + (boxStudentSelection.getValue() + 1));
    }

    public static Group getCurrentGroup() {
        return currentGroup;
    }

    public void addStudentToTable() {
        tableStudentList.setItems(currentGroup.getStudentsForTable());
        tableStudentList.refresh();
    }

    private void uponYearSelection(ActionEvent actionEvent) {
        choiceMonth.getItems().clear();
        choiceMonth.getItems().addAll(months);
    }
    private void uponMonthSelection(ActionEvent actionEvent) {
        remakeTable();
    }

    private void remakeTable() {
        MainTable mainTable = new MainTable(this);
        TableView<Student> tableView = mainTable.createTableView();
        tableStudentList.getColumns().setAll(tableView.getColumns());
    }

    public int getYear() {
        if (!firstLoadComplete) return 2024;
        else return choiceYear.getValue();
    }

    public String getMonth() {
        if (!firstLoadComplete) return month;
        else return choiceMonth.getValue();
    }
}