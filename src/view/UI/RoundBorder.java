package view.UI;

import javax.swing.border.Border;
import java.awt.*;

public class RoundBorder implements Border {
    private Color color;
    private int arcWidth;
    private int arcHeight;

    public RoundBorder(Color color, int arcWidth, int arcHeight) {// 有参数的构造方法
        this.color = color;
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
    }

    public RoundBorder(Color color) {// 有参数的构造方法
        this.color = color;
    }

    public RoundBorder() {// 无参构造方法
        this.color = Color.BLACK;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }

    public boolean isBorderOpaque() {
        return false;
    }

    // 实现Border（父类）方法
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width,
                            int height) {
        g.setColor(color);
        if (arcWidth == 0 || arcHeight == 0) {
            g.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, c.getHeight(), c.getHeight());
        } else {
            g.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, arcWidth, arcHeight);
        }
    }
}