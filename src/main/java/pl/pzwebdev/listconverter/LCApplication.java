package pl.pzwebdev.listconverter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LCApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Ensure database and table are initialized
        DatabaseConnect.createNewDatabase();
        DatabaseConnect.createNewTable();

        FXMLLoader fxmlLoader = new FXMLLoader(LCApplication.class.getResource("views/%s.fxml".formatted("main-view")));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 780);

        // Load style.
        scene.getStylesheets().add(String.valueOf(LCApplication.class.getResource("css/style.css")));
        stage.setResizable(false);
        stage.setTitle("List Converter");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}