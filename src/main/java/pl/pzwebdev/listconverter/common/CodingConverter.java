package pl.pzwebdev.listconverter.common;

import java.io.*;
import java.nio.charset.StandardCharsets;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.*;

public class CodingConverter {

    private static final String UTF_8 = "UTF-8";
    private static final String CP852 = "CP852";

    public static void convertToUTF8(String input, String output) {
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
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas konwersji pliku: " + e.getMessage());
        }
    }

    public static void convertToPDF(String input, String output) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            PDFont font = PDType0Font.load(document, new File("Consolas.ttf"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(input), StandardCharsets.UTF_8));

            String line;
            float currentYPosition = 780;
            boolean startNewPage = false;
            boolean isFirstPage = true;
            boolean isTitle = false;

            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("\u001B", "");
                line = replaceSpecialCharacters(line);

                if (line.startsWith("36") || line.startsWith("H") || line.startsWith("2")) {
                    line = line.replaceAll("36", "");
                    line = line.replaceAll("H", "");
                    line = line.replaceAll("2", "");
                }


                if (line.contains("\f")) {
                    startNewPage = true;
                    isFirstPage = false;
                }

                if (line.contains("NAJLEPSZA PIĄTKA HODOWCÓW (PKT-1) Z LOTU")) {
                    System.out.println("NAJLEPSZA PIĄTKA HODOWCÓW (PKT-1) Z LOTU");
                    isFirstPage = true;
                }

                if (line.contains("GW1")) {
                    isTitle = true;
                    continue;
                }

                // End of title.
                if (line.contains("HW0") || line.contains("W0")) {
                    isTitle = false;
                    continue;
                }

                String[] lines = line.split("\\f");
                for (String l : lines) {
                    int marginLeft = 5;
                    int fontSize = 9;

                    if (isFirstPage) {
                        marginLeft = 40;
                        fontSize = 11;
                    }

                    if (startNewPage) {
                        contentStream.close();
                        page = new PDPage();
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        currentYPosition = page.getMediaBox().getHeight();
                        startNewPage = false;
                    }

                    if (isTitle) {
                        contentStream.setFont(font, 20);
                        currentYPosition -= 50;
                    } else {
                        contentStream.setFont(font, fontSize);
                        currentYPosition -= 10;
                    }

                    contentStream.beginText();
                    contentStream.newLineAtOffset(marginLeft, currentYPosition); // Set margin left
                    contentStream.showText(l);
                    contentStream.endText();
                }
            }

            contentStream.close();
            document.save(output);
            document.close();

        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas konwersji pliku: " + e.getMessage());
        }
    }

    private static String replaceSpecialCharacters(String line) {
        line = line.replace("\u000F", "");  // U+000F () -> usunięcie
        line = line.replace("\u0012", "");  // U+0012 () -> usunięcie

        return line;
    }

}
