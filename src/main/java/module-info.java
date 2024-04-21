module pl.pzwebdev.listconverter {
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens pl.pzwebdev.listconverter to javafx.fxml;

    exports pl.pzwebdev.listconverter;
}