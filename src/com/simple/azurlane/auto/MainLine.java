package com.simple.azurlane.auto;

import com.simple.azurlane.util.PropertyUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainLine {
    private static ScheduledExecutorService scheduledService;

    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private static void autoWork() {
        // load config from cache
        int x1;
        int y1;
        int x2;
        int y2;
        try {
            int z = PropertyUtil.getInt("z");
            x1 = PropertyUtil.getInt("x1");
            y1 = PropertyUtil.getInt("y1");
            x2 = PropertyUtil.getInt("x2") / z;
            y2 = PropertyUtil.getInt("y2") / z;
        } catch (NumberFormatException e) {
            return;
        }
        List<String> rl = Arrays.asList(PropertyUtil.getString("r").split(","));
        List<String> gl = Arrays.asList(PropertyUtil.getString("g").split(","));
        List<String> bl = Arrays.asList(PropertyUtil.getString("b").split(","));

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

    public static Robot getRobot() {
        return robot;
    }

    /**
     * 校验一组颜色是否与之指定像素点颜色匹配
     *
     * @param pixelColor 像素点颜色
     * @return true/false
     */
    public static boolean validatePixelColor(List<String> rl, List<String> gl, List<String> bl, Color pixelColor) {
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
