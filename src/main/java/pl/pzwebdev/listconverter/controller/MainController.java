package pl.pzwebdev.listconverter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pl.pzwebdev.listconverter.common.CodingConverter;
import pl.pzwebdev.listconverter.common.UserPreferencesSettings;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public Button convertButton;
    public Label directoryTxtFiles;
    public Label directoryPdfFiles;
    public Text messageText;
    public TextField txtPath;
    public TextField pdfPath;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pdfPath.setText(UserPreferencesSettings.loadPdfPath());
        txtPath.setText(UserPreferencesSettings.loadTxtPath());
    }

    @FXML
    public void convertAction(ActionEvent actionEvent) {
        // Save path
        String pdfPathValue = pdfPath.getText();
        String txtPathValue = txtPath.getText();
        UserPreferencesSettings.savePdfPath(pdfPathValue);
        UserPreferencesSettings.saveTxtPath(txtPathValue);

        String sciezkaWejscia = "file/LKON_S01.TXT";
        String sciezkaWyjscia = "file/LKON_S01_Coonverted.TXT";
        String sciezkaWyjsciaPDF = "file/LKON_S01_CoonvertedPDF.pdf";

        boolean convertToUtf8 = CodingConverter.convertToUTF8(sciezkaWejscia, sciezkaWyjscia);
        boolean convertToPDF = CodingConverter.convertToPDF(sciezkaWyjscia, sciezkaWyjsciaPDF);

        if (!convertToUtf8 || !convertToPDF) {
            messageText.setText("Wystąpił problem z generowaniem plików.");
            messageText.setStyle("-fx-text-fill: red;");

        } else {
            messageText.setText("Poprawnie wygenerowany pliki.");
            messageText.setStyle("-fx-text-fill: #00ff00;");
        }
    }

}