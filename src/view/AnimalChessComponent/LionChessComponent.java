package view.AnimalChessComponent;

import model.ChessboardPoint;
import model.PlayerColor;
import view.ChessComponent;

import javax.swing.*;
import java.awt.*;

public class LionChessComponent extends ChessComponent {
    private ImageIcon icon = new ImageIcon();
    public LionChessComponent(PlayerColor owner, int size) {
        super(owner,size);
        if(owner == PlayerColor.BLUE){
            icon = new ImageIcon(getClass().getResource("/AnimalIcons/lionLeft.png"));
        }else icon = new ImageIcon(getClass().getResource("/AnimalIcons/lionRight.png"));
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
