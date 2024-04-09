module com.registry.studentregistrysystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires org.apache.pdfbox;
    requires itextpdf;

//    requires com.almasb.fxgl.all;

    opens com.registry.studentregistrysystem to javafx.fxml;
    exports com.registry.studentregistrysystem;
}