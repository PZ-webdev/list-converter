package pl.pzwebdev.listconverter.common;

import java.io.*;

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
}
