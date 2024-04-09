package com.registry.studentregistrysystem;

import StudentData.Group;
import StudentData.Student;
import TableViews.MainTable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static ArrayList<Group> groups = new ArrayList<>();
    public int counter = -2;

    private ManagerController managerController;

    public Controller() {}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerController = new ManagerController(this);

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

//        boxStudentSelection.setOnAction(this::switchGroup);
        boxStudentSelection.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                switchGroup(newValue);
            }
        });

        choiceYear.setValue(2024);
        choiceMonth.setValue("April");

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
        fxmlLoader.setController(managerController);
        Scene manager = new Scene(fxmlLoader.load(), 1225, 813);
        Stage stage = new Stage();

        stage.setScene(manager);
        stage.show();

        managerController.refreshTable();
    }

    public void makeNewGroup() {
        Group group = new Group(counter);
        groups.add(group);
        currentGroup = group;
        if(currentGroup.groupId == -2) boxStudentSelection.getItems().add("All students");
        else if(currentGroup.groupId == -1) boxStudentSelection.getItems().add("Ungrouped students");
        else {
            boxStudentSelection.getItems().add("Group " + (counter + 1));
            boxStudentSelection.setValue("Group " + (counter + 1));
        }
        System.out.println("New group made! counter: " + counter);
        counter++;
    }

    public void switchGroup(String selectedValue) {
        if (selectedValue == null) {
            return; // Nothing selected yet
        }

        if (Objects.equals(boxStudentSelection.getValue(), "All students")) currentGroup = groups.getFirst();
        else if (Objects.equals(boxStudentSelection.getValue(), "Ungrouped students")) currentGroup = groups.get(1);
        else {
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(boxStudentSelection.getValue());
            if (matcher.find()) {
                int groupNumber = Integer.parseInt(matcher.group()); // Extract the matched number as an integer
                currentGroup = groups.get(groupNumber + 1);
            }
        }

        remakeTable();
        managerController.refreshTable();
        labelGroupSelection.setText("Current group: " + boxStudentSelection.getValue());

        System.out.println("Current group: " + currentGroup.groupId);
    }

    public static Group getCurrentGroup() {
        return currentGroup;
    }
    public static Group getUngroupedGroup() {
        return groups.get(1);
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

    public void remakeTable() {
        MainTable mainTable = new MainTable(this);
        TableView<Student> tableView = mainTable.createTableView();
        tableStudentList.getColumns().setAll(tableView.getColumns());
        tableStudentList.setItems(currentGroup.getStudentsForTable());
    }

    public int getYear() {
        if (!firstLoadComplete) return 2024;
        else return choiceYear.getValue();
    }

    public String getMonth() {
        if (!firstLoadComplete) return month;
        else return choiceMonth.getValue();
    }

    public static ArrayList<Group> getGroupsArray() {
        return groups;
    }
}