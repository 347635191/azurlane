package com.simple.azurlane.view;

import com.simple.azurlane.auto.MainLine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class MainFrame {
    public MainFrame() {
        SystemTray systemTray = SystemTray.getSystemTray();
        PopupMenu pop = new PopupMenu();
        MenuItem control = new MenuItem("start");
        MenuItem exit = new MenuItem("exit");
        pop.add(control);
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

    public static void main(String[] args) {
        new MainFrame();
    }
}
