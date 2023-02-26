package com.simple.azurlane.view;

import com.simple.azurlane.auto.MainLine;
import com.simple.azurlane.util.PropertyUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class MainFrame {
    public MainFrame() {
        //系统托盘
        SystemTray systemTray = SystemTray.getSystemTray();
        //菜单栏
        PopupMenu pop = new PopupMenu();
        MenuItem control = new MenuItem("start");
        MenuItem config = new MenuItem("config");
        MenuItem loadColor = new MenuItem("loadColor");
        MenuItem exit = new MenuItem("exit");
        pop.add(control);
        pop.addSeparator();
        pop.add(config);
        pop.addSeparator();
        pop.add(loadColor);
        pop.addSeparator();
        pop.add(exit);

        control.addActionListener(e -> {
            if (control.getLabel().equals("start")) {
                MainLine.start();
                control.setLabel("stop");
            } else {
                MainLine.stop();
                control.setLabel("start");
            }
        });

        config.addActionListener(e -> {
            ConfigFrame frame = ConfigFrame.getFrame();
            frame.setVisible(true);
            frame.setExtendedState(Frame.NORMAL);
        });

        loadColor.addActionListener(e -> {
            int x;
            int y;
            try {
                x = PropertyUtil.getInt("x1");
                y = PropertyUtil.getInt("y1");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "识别的像素坐标有误");
                return;
            }

            String r = PropertyUtil.getString("r");
            String g = PropertyUtil.getString("g");
            String b = PropertyUtil.getString("b");
            List<String> rl = Arrays.asList(r.split(","));
            List<String> gl = Arrays.asList(g.split(","));
            List<String> bl = Arrays.asList(b.split(","));
            Robot robot = MainLine.getRobot();
            Color pixelColor = robot.getPixelColor(x, y);
            if (MainLine.validatePixelColor(rl, gl, bl, pixelColor)) {
                return;
            }

            //刷新property的rgb属性
            Map<String, String> configMap = new HashMap<>();
            configMap.put("r", completion(r, String.valueOf(pixelColor.getRed())));
            configMap.put("g", completion(g, String.valueOf(pixelColor.getGreen())));
            configMap.put("b", completion(b, String.valueOf(pixelColor.getBlue())));
            PropertyUtil.alter(configMap);

            //刷新配置页的rgb属性
            if(ConfigFrame.frameCreated()){
                ConfigFrame frame = ConfigFrame.getFrame();
                frame.loadConfig();
            }
        });

        exit.addActionListener(e -> System.exit(0));

        try {
            TrayIcon trayIcon = new TrayIcon(ImageIO.read(Objects.requireNonNull(MainFrame.class.getClassLoader().getResourceAsStream("azurlane.jpg"))), "碧蓝航线", pop);
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("碧蓝航线");
            systemTray.add(trayIcon);
        } catch (IOException | AWTException e) {
            e.printStackTrace();
        }
    }

    private String completion(String oldColor, String newColor) {
        if (oldColor.trim().equals("")) {
            return newColor;
        }
        return oldColor + "," + newColor;
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
