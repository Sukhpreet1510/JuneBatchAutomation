package com.envision.automation.framework.Utils;

import java.io.IOException;
import java.util.Properties;

public class ORUtils {
    static Properties ORProperties;

    public static void loadORFile() throws IOException {
        ORProperties = PropertiesLoader.loadPropertiesFile(System.getProperty("user.dir") +
                "\\src\\main\\resources\\configs\\OR.properties");
    }
    public static String getORPropertyValue(String ORPropertyName) {
        return PropertiesLoader.getPropertyValue(ORPropertyName, ORProperties);
    }
}
