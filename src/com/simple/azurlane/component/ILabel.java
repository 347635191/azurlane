package com.simple.azurlane.component;

import javax.swing.*;
import java.awt.*;

public class ILabel extends JLabel {
    public ILabel(String text, int x, int y, int w, int h) {
        super(text);
        setFont(new Font("Arial", Font.BOLD, 30));
        setForeground(new Color(225, 6, 2));
        setBounds(x, y, w, h);
    }
}
