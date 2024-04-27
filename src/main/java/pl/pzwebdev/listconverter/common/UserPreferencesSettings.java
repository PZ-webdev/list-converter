package pl.pzwebdev.listconverter.common;

import java.util.prefs.Preferences;

public class UserPreferencesSettings {
    private static final String PDF_PATH_KEY = "pdfPath";
    private static final String TXT_PATH_KEY = "txtPath";

    public static void savePdfPath(String path) {
        Preferences prefs = Preferences.userNodeForPackage(UserPreferencesSettings.class);
        prefs.put(PDF_PATH_KEY, path);
    }

    public static String loadPdfPath() {
        Preferences prefs = Preferences.userNodeForPackage(UserPreferencesSettings.class);
        return prefs.get(PDF_PATH_KEY, "");
    }

    public static void saveTxtPath(String path) {
        Preferences prefs = Preferences.userNodeForPackage(UserPreferencesSettings.class);
        prefs.put(TXT_PATH_KEY, path);
    }

    public static String loadTxtPath() {
        Preferences prefs = Preferences.userNodeForPackage(UserPreferencesSettings.class);
        return prefs.get(TXT_PATH_KEY, "");
    }
}
