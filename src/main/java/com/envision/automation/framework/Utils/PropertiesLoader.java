package com.envision.automation.framework.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    public static Properties loadPropertiesFile(String fileName) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(fileName)));
        return properties;
    }
    public static String getPropertyValue(String propertyName, Properties properties){
        return properties.getProperty(propertyName);

    }
}
