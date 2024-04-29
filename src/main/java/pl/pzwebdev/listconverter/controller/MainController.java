package pl.pzwebdev.listconverter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import pl.pzwebdev.listconverter.common.CodingConverter;
import pl.pzwebdev.listconverter.common.UserPreferencesSettings;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public Button convertButton;
    public Label directoryTxtFiles;
    public Label directoryPdfFiles;
    public Label messageText;
    public TextField txtPath;
    public TextField pdfPath;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pdfPath.setText(UserPreferencesSettings.loadPdfPath());
        txtPath.setText(UserPreferencesSettings.loadTxtPath());
    }

    @FXML
    public void convertAction(ActionEvent actionEvent) {
        // Save user path preferences
        String txtPathValue = txtPath.getText();
        String pdfPathValue = pdfPath.getText();
        UserPreferencesSettings.saveTxtPath(txtPathValue);
        UserPreferencesSettings.savePdfPath(pdfPathValue);

        try {
            List<String> txtFilePaths = listTextFiles(txtPathValue);

            for (String txtFilePath : txtFilePaths) {
                String convertedFile = pdfPathValue + "/" + Paths.get(txtFilePath).getFileName().toString().replace(".TXT", "_converted.TXT");
                String pdfFilename = pdfPathValue + "/" + Paths.get(txtFilePath).getFileName().toString().replace(".TXT", ".pdf");

                // Convert to UTF-8
                CodingConverter.convertToUTF8(txtFilePath, convertedFile);

                // Convert to PDF
                CodingConverter.convertToPDF(convertedFile, pdfFilename);
                displaySuccessMessage("Plik PDF został pomyślnie utworzony.");
            }
        } catch (FileNotFoundException e) {
            displayErrorMessage(e.getMessage());
        } catch (Exception e) {
            displayErrorMessage("Wystąpił nieoczekiwany błąd.");
        }
    }

    public static List<String> listTextFiles(String directoryPath) throws FileNotFoundException {
        List<String> textFiles = new ArrayList<>();
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            throw new FileNotFoundException("Podany katalog nie istnieje: " + directoryPath);
        }

        listTextFilesRecursive(directory, textFiles);

        return textFiles;
    }

    private static void listTextFilesRecursive(File directory, List<String> textFiles) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if ((file.isFile() && file.getName().toLowerCase().endsWith(".txt"))
                        && !file.getName().toLowerCase().endsWith("_converted.txt")) {
                    textFiles.add(file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    listTextFilesRecursive(file, textFiles);
                }
            }
        }
    }

    private void displaySuccessMessage(String message) {
        messageText.setText(message);
        messageText.setStyle("-fx-text-fill: #00ff00;");
        messageText.setVisible(true);
    }

    private void displayErrorMessage(String message) {
        messageText.setText(message);
        messageText.setStyle("-fx-text-fill: red;");
        messageText.setVisible(true);
    }
}