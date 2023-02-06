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

    private static void autoWork() {
        int z = Integer.parseInt(properties.getProperty("z"));
        int x1 = Integer.parseInt(properties.getProperty("x1")) / z;
        int y1 = Integer.parseInt(properties.getProperty("y1")) / z;
        int x2 = Integer.parseInt(properties.getProperty("x2")) / z;
        int y2 = Integer.parseInt(properties.getProperty("y2")) / z;

        List<String> rl = Arrays.asList(properties.getProperty("r").split(","));
        List<String> gl = Arrays.asList(properties.getProperty("g").split(","));
        List<String> bl = Arrays.asList(properties.getProperty("b").split(","));

        Color pixelColor = robot.getPixelColor(x1, y1);
        if (rl.contains(String.valueOf(pixelColor.getRed())) && gl.contains(String.valueOf(pixelColor.getGreen())) && bl.contains(String.valueOf(pixelColor.getBlue()))) {
            robot.mouseMove(x2, y2);
            robot.mousePress(KeyEvent.BUTTON1_MASK);
            robot.delay(200);
            robot.mouseRelease(KeyEvent.BUTTON1_MASK);
            robot.delay(200);
            robot.mouseMove(0, 0);
        }
    }

    public static void start() {
        scheduledService = Executors.newScheduledThreadPool(1);
        scheduledService.scheduleAtFixedRate(MainLine::autoWork, 0, 2, TimeUnit.SECONDS);
    }

    public static void stop() {
        scheduledService.shutdownNow();
        scheduledService = null;
        System.gc();
    }
}
