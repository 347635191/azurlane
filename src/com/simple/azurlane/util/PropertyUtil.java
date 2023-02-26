package com.simple.azurlane.util;

import com.simple.azurlane.global.FileConstant;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyUtil {
    private static final Properties properties = new Properties();

    private static Map<String, String> configMap = new HashMap<>();

    private PropertyUtil() {
    }

    static {
        try {
            properties.load(new FileReader(FileConstant.PROPERTIES_PATH));
            configMap.put("z", properties.getProperty("z"));
            configMap.put("x1", properties.getProperty("x1"));
            configMap.put("y1", properties.getProperty("y1"));
            configMap.put("x2", properties.getProperty("x2"));
            configMap.put("y2", properties.getProperty("y2"));
            configMap.put("r", properties.getProperty("r"));
            configMap.put("g", properties.getProperty("g"));
            configMap.put("b", properties.getProperty("b"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getInt(String key) {
        return Integer.parseInt(configMap.get(key));
    }

    public static String getString(String key) {
        return configMap.get(key);
    }

    public static void alter(Map<String, String> newMap) {
        //刷新property
        for (Map.Entry<String, String> entry : newMap.entrySet()) {
            properties.setProperty(entry.getKey(), entry.getValue());
        }
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(FileConstant.PROPERTIES_PATH))) {
            properties.store(bos, null);
            bos.flush();
        } catch (IOException ignore) {
        }

        //刷新cache
        for (Map.Entry<String, String> entry : newMap.entrySet()) {
            configMap.put(entry.getKey(), entry.getValue());
        }
    }
}