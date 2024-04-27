module pl.pzwebdev.listconverter {
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.pdfbox;

    opens pl.pzwebdev.listconverter to javafx.fxml;
    opens pl.pzwebdev.listconverter.controller to javafx.fxml;

    exports pl.pzwebdev.listconverter;
    exports pl.pzwebdev.listconverter.controller;
}