package view.UI;

import music.MusicThread;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class RoundButton extends JButton {

    public RoundButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBackground(new Color(139, 69, 19));
        setForeground(Color.white);
        // Create a rounded border with padding
        int radius = 10;
        int padding = 6;
        Border border = BorderFactory.createEmptyBorder(padding, padding, padding, padding);
        Border roundedBorder = new RoundBorder(getForeground()); // 使用RoundBorder创建圆角边框
        Border compound = BorderFactory.createCompoundBorder(roundedBorder, border);
        setBorder(compound);

        // Add a mouse listener to change the background color when the button is pressed
        buttonAbove(this);
    }

    private void buttonAbove(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(49, 36, 1));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(139, 69, 19));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                button.setBackground(new Color(49, 3, 1)); // 鼠标点击时颜色变为绿色
                File musicFile = new File("resource/Music/click.wav");

                URL musicURL;
                try {
                    musicURL = musicFile.toURI().toURL();
                } catch (MalformedURLException e1) {
                    throw new RuntimeException(e1);
                }
                // 创建音乐线程实例
                MusicThread musicThread = new MusicThread(musicURL, false);

                // 创建线程并启动
                Thread music = new Thread(musicThread);
                music.start();
                musicThread.setVolume(0.5f);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
        g2.dispose();

        super.paintComponent(g);
    }
}