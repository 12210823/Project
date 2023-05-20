package view.AnimalChessComponent;

import model.PlayerColor;
import view.ChessComponent;

import javax.swing.*;
import java.awt.*;

public class RatChessComponent extends ChessComponent {
    private ImageIcon icon = new ImageIcon();
    public RatChessComponent(PlayerColor owner, int size) {
        super(owner,size);
        if(owner == PlayerColor.BLUE){
            icon = new ImageIcon("resource/AnimalIcons/ratLeft.png");
        }else icon = new ImageIcon("resource/AnimalIcons/ratRight.png");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(icon != null){
            g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}
