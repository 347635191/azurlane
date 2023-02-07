package com.simple.azurlane.auto;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainLine {
    private static ScheduledExecutorService scheduledService;

    private static final Properties properties = new Properties();

    private static Robot robot;

    static {
        try {
            robot = new Robot();
            properties.load(MainLine.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
    }

    static int z = Integer.parseInt(properties.getProperty("z"));
    static int x1 = Integer.parseInt(properties.getProperty("x1"));
    static int y1 = Integer.parseInt(properties.getProperty("y1"));
    static int x2 = Integer.parseInt(properties.getProperty("x2")) / z;
    static int y2 = Integer.parseInt(properties.getProperty("y2")) / z;
    static List<String> rl = Arrays.asList(properties.getProperty("r").split(","));
    static List<String> gl = Arrays.asList(properties.getProperty("g").split(","));
    static List<String> bl = Arrays.asList(properties.getProperty("b").split(","));

    private static void autoWork() {
        Color pixelColor = robot.getPixelColor(x1, y1);
        if (validatePixelColor(rl, gl, bl, pixelColor)) {
            robot.mouseMove(x2, y2);
            robot.mousePress(KeyEvent.BUTTON1_MASK);
            robot.delay(200);
            robot.mouseRelease(KeyEvent.BUTTON1_MASK);
            robot.delay(200);
            robot.mouseMove(0, 0);
        }
    }

    private static boolean validatePixelColor(List<String> rl, List<String> gl, List<String> bl, Color pixelColor) {
        for (int i = 0; i < rl.size(); i++) {
            if (String.valueOf(pixelColor.getRed()).equals(rl.get(i)) && String.valueOf(pixelColor.getGreen()).equals(gl.get(i)) && String.valueOf(pixelColor.getBlue()).equals(bl.get(i))) {
                return true;
            }
        }
        return false;
    }

    public static void start() {
        scheduledService = Executors.newScheduledThreadPool(1);
        scheduledService.scheduleAtFixedRate(MainLine::autoWork, 0, 3, TimeUnit.SECONDS);
    }

    public static void stop() {
        scheduledService.shutdownNow();
        scheduledService = null;
        System.gc();
    }
}
