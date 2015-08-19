package resource;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigurationManager {
    private static Locale locale = Locale.ENGLISH;
    private final static ResourceBundle resourceBundle =
            ResourceBundle.getBundle("config");
    // класс извлекает информацию из файла config.properties
    private ConfigurationManager() { }
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
