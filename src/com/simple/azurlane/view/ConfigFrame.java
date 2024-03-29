package com.simple.azurlane.view;

import com.simple.azurlane.component.IButton;
import com.simple.azurlane.component.ILabel;
import com.simple.azurlane.component.IText;
import com.simple.azurlane.util.PropertyUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConfigFrame extends JFrame {
    private final JLabel xl = new ILabel("X", 34, 30, 20, 20);
    private final JLabel yl = new ILabel("Y", 34, 90, 20, 20);
    private final JLabel rl = new ILabel("R", 34, 150, 24, 20);
    private final JLabel gl = new ILabel("G", 34, 210, 24, 20);
    private final JLabel bl = new ILabel("B", 34, 270, 24, 20);
    // x1
    private final JTextField x = new IText(100, 25, 160, 30);
    // y1
    private final JTextField y = new IText(100, 85, 160, 30);
    private final JTextField r = new IText(100, 145, 160, 30);
    private final JTextField g = new IText(100, 205, 160, 30);
    private final JTextField b = new IText(100, 265, 160, 30);
    public final JButton button1 = new IButton("Cancel", 34, 320, 96, 30);
    public final JButton button2 = new IButton("Apply", 164, 320, 96, 30);
    private volatile static ConfigFrame configFrame;

    private ConfigFrame() throws HeadlessException {
        setTitle("config");
        setSize(300, 400);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setFocusable(true);
        getContentPane().setBackground(new Color(255, 255, 255));
        try {
            setIconImage(ImageIO.read(Objects.requireNonNull(ConfigFrame.class.getClassLoader().getResourceAsStream("azurlane.jpg"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadConfig();
        loadComponent();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });
        setVisible(true);
    }

    private void loadComponent() {
        Container contentPane = getContentPane();
        contentPane.add(xl);
        contentPane.add(yl);
        contentPane.add(rl);
        contentPane.add(gl);
        contentPane.add(bl);
        contentPane.add(x);
        contentPane.add(y);
        contentPane.add(r);
        contentPane.add(g);
        contentPane.add(b);
        contentPane.add(b);
        contentPane.add(b);
        contentPane.add(button1);
        contentPane.add(button2);
    }

    public void loadConfig() {
        x.setText(PropertyUtil.getString("x1"));
        y.setText(PropertyUtil.getString("y1"));
        r.setText(PropertyUtil.getString("r"));
        g.setText(PropertyUtil.getString("g"));
        b.setText(PropertyUtil.getString("b"));
    }

    public static ConfigFrame getFrame() {
        if (configFrame == null) {
            configFrame = new ConfigFrame();
        }
        return configFrame;
    }

    public static boolean frameCreated(){
        return configFrame != null;
    }

    public void changeValue() {
        String xText = x.getText().trim();
        String yText = y.getText().trim();
        String rText = r.getText().trim();
        String gText = g.getText().trim();
        String bText = b.getText().trim();
        if(splitEqual(xText, yText)){
            JOptionPane.showMessageDialog(null, "X,Y数量不一致");
            return;
        }
        if(splitEqual(rText, gText, bText)){
            JOptionPane.showMessageDialog(null, "R,G,B数量不一致");
            return;
        }
        if (!(x.getText().equals(PropertyUtil.getString("x1")) && y.getText().equals(PropertyUtil.getString("y1")) && r.getText().equals(PropertyUtil.getString("r")) && g.getText().equals(PropertyUtil.getString("g")) && b.getText().equals(PropertyUtil.getString("b")))) {
            Map<String, String> configMap = new HashMap<>(16);
            configMap.put("x1", xText);
            configMap.put("y1", yText);
            configMap.put("r", rText);
            configMap.put("g", gText);
            configMap.put("b", bText);
            PropertyUtil.alter(configMap);
        }
    }

    /**
     * 按照英文逗号分割,检查数量是否一致
     * @param str 输入
     * @return 按照英文逗号分割，分割数是否不一致
     */
    private static boolean splitEqual(String... str) {
        int preLen = -2;
        for (String s : str) {
            int nextLen;
            if(s.length() == 0){
                nextLen = -1;
            }else{
                nextLen = s.split(",").length;
            }
            if (nextLen != preLen &&  preLen != -2) {
                return true;
            }
            preLen = nextLen;
        }
        return false;
    }
}
