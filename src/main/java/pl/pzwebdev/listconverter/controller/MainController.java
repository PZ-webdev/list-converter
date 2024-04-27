package pl.pzwebdev.listconverter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pl.pzwebdev.listconverter.common.CodingConverter;


public class MainController {

    @FXML
    public Button convertButton;

    @FXML
    public void convertAction(ActionEvent actionEvent) {
        String sciezkaWejscia = "file/LKON_S01.TXT";
        String sciezkaWyjscia = "file/LKON_S01_Coonverted.TXT";
        String sciezkaWyjsciaPDF = "file/LKON_S01_CoonvertedPDF.pdf";

        CodingConverter.convertToUTF8(sciezkaWejscia, sciezkaWyjscia);
        CodingConverter.convertToPDF(sciezkaWyjscia, sciezkaWyjsciaPDF);
    }
}