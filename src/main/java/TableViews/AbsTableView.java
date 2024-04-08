package TableViews;

import javafx.scene.control.TableView;

public abstract class AbsTableView<T> implements IntTableView<T> {
    public abstract TableView<T> createTableView();
}
