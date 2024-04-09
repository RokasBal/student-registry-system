module com.registry.studentregistrysystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;

//    requires com.almasb.fxgl.all;

    opens com.registry.studentregistrysystem to javafx.fxml;
    exports com.registry.studentregistrysystem;
}