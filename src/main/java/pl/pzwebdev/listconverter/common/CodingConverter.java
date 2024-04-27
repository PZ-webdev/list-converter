package pl.pzwebdev.listconverter.common;

import java.io.*;
import java.nio.charset.StandardCharsets;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.*;

public class CodingConverter {

    private static final String UTF_8 = "UTF-8";
    private static final String CP852 = "CP852";

    public static boolean convertToUTF8(String input, String output) {
        try {
            FileInputStream fileInputStream = new FileInputStream(input);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, CP852);

            FileOutputStream fileOutputStream = new FileOutputStream(output);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, UTF_8);

            int c;
            while ((c = inputStreamReader.read()) != -1) {
                outputStreamWriter.write(c);
            }

            inputStreamReader.close();
            outputStreamWriter.close();

            System.out.println("Plik przekonwertowany na " + UTF_8 + ".");
            return true;
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas konwersji pliku: " + e.getMessage());
            return false;
        }
    }

    public static boolean convertToPDF(String sciezkaWejscia, String sciezkaWyjscia) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            PDFont font = PDType0Font.load(document, new File("Consolas.ttf"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(sciezkaWejscia), StandardCharsets.UTF_8));

            String line;
            float currentYPosition = 770;
            boolean startNewPage = false;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("\u001B", "");
                line = replaceSpecialCharacters(line);

                if (line.contains("\f")) {
                    startNewPage = true;
                }

                String[] lines = line.split("\\f");
                for (String l : lines) {
                    if (startNewPage) {
                        contentStream.close();
                        page = new PDPage();
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        currentYPosition = page.getMediaBox().getHeight() - 10;
                        startNewPage = false;
                    }

                    contentStream.beginText();
                    contentStream.setFont(font, 9);
                    contentStream.newLineAtOffset(5, currentYPosition);
                    contentStream.showText(l);
                    contentStream.endText();
                    currentYPosition -= 10;
                }
            }

            contentStream.close();
            document.save(sciezkaWyjscia);
            document.close();

            System.out.println("Plik przekonwertowany na PDF.");
            return true;
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas konwersji pliku: " + e.getMessage());
            return false;
        }
    }

    private static String replaceSpecialCharacters(String line) {
        line = line.replace("\u000F", "");  // U+000F () -> usunięcie
        line = line.replace("\u0012", "");  // U+0012 () -> usunięcie

        return line;
    }

}
