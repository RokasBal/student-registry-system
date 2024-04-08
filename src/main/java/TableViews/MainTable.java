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
            TableColumn<Student, String> column = new TableColumn<>("" + i);
            column.setPrefWidth(30);
            StringProperty string = new SimpleStringProperty("x");
            column.setCellValueFactory(data -> string);
            tableView.getColumns().add(column);
        }

        ScrollPane scrollPane = new ScrollPane(tableView);
        scrollPane.setFitToWidth(true);

        return tableView;
    }
}
