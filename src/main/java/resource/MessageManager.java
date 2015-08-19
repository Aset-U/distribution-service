package resource;

import java.util.Locale;
import java.util.ResourceBundle;


public class MessageManager {

    private final static ResourceBundle resourceBundle =
            ResourceBundle.getBundle("messages", Locale.ENGLISH);
    // класс извлекает информацию из файла messages.properties
    private MessageManager() { }
    public static String getProperty(String key) {
        return resourceBundle.getString(key);}

    }
