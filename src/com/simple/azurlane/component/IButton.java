package com.simple.azurlane.component;

import com.simple.azurlane.view.ConfigFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IButton extends JButton {
    private ConfigFrame configFrame;

    public IButton(String text, int x, int y, int w, int h) {
        super(text);
        setBounds(x, y, w, h);
        setFont(new Font("Arial", Font.BOLD, 16));
        setBackground(new Color(255, 255, 255));
        setForeground(new Color(51, 51, 51));
        setFocusPainted(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    JButton jButton = (JButton) e.getSource();
                    switch (jButton.getText()) {
                        case "Cancel": {
                            cancel();
                            break;
                        }
                        case "Apply": {
                            apply();
                            break;
                        }
                        default:
                    }
                }
            }
        });
    }

    private void cancel() {
        loadFrame();
        configFrame.button1.setEnabled(false);
        configFrame.loadConfig();
        configFrame.button1.setEnabled(true);
    }

    private void apply() {
        loadFrame();
        configFrame.button2.setEnabled(false);
        configFrame.changeValue();
        configFrame.button2.setEnabled(true);
    }

    private void loadFrame() {
        if (configFrame == null) {
            configFrame = ConfigFrame.getFrame();
        }
    }
}
