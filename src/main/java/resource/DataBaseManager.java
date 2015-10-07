package resource;

import java.util.Locale;
import java.util.ResourceBundle;

public class DataBaseManager {
    private static Locale locale = Locale.ENGLISH;
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
    // класс извлекает информацию из файла db.properties
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
