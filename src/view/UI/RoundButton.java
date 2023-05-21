package view.UI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundButton extends JButton {
    private static final int NUM_STEPS = 12; // 渐变的步数
    private static final int DELAY = 1; //每一步的延迟时间（以毫秒为单位）

    private static int currentStep = 0; // 当前步数
    private static Timer timer; // 定时器
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
                startColorTransition(button, new Color(49, 36, 1));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                startColorTransition(button, new Color(139, 69, 19));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                button.setBackground(new Color(49, 3, 1)); // 鼠标点击时颜色变为绿色
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
    private static void startColorTransition(JButton button, Color targetColor) {
        if (timer != null && timer.isRunning()) {
            return; // 如果渐变动画已经在进行中，则直接返回
        }

        Color initialColor = button.getBackground();
        currentStep = 0; // 重置当前步数

        timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentStep >= NUM_STEPS) {
                    timer.stop(); // 达到渐变的总步数后停止定时器
                } else {
                    float ratio = (float) currentStep / NUM_STEPS; // 计算当前步数与总步数的比例
                    Color transitionColor = getTransitionColor(initialColor, targetColor, ratio); // 计算过渡颜色
                    button.setBackground(transitionColor);
                    currentStep++;
                }
            }
        });

        timer.start();
    }

    private static Color getTransitionColor(Color initialColor, Color targetColor, float ratio) {
        int red = (int) (initialColor.getRed() + (targetColor.getRed() - initialColor.getRed()) * ratio);
        int green = (int) (initialColor.getGreen() + (targetColor.getGreen() - initialColor.getGreen()) * ratio);
        int blue = (int) (initialColor.getBlue() + (targetColor.getBlue() - initialColor.getBlue()) * ratio);
        return new Color(red, green, blue);
    }
}