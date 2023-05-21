package view.UI;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

// ImagePanel 类
public class ImagePanel extends JPanel {
    private Image backgroundImage;

    public ImagePanel(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        this.backgroundImage = imageIcon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public void setBackgroundImage(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        this.backgroundImage = imageIcon.getImage();
    }
}