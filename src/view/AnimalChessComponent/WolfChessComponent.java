package view.AnimalChessComponent;

import model.ChessboardPoint;
import model.PlayerColor;
import view.ChessComponent;

import javax.swing.*;
import java.awt.*;

public class WolfChessComponent extends ChessComponent {
    private ImageIcon icon = new ImageIcon();
    public WolfChessComponent(PlayerColor owner, int size, int style) {
        super(owner,size,style);
        if(style == 0){
            if(owner == PlayerColor.BLUE){
                icon = new ImageIcon("resource/AnimalIcons/wolfLeft.png");
            }else icon = new ImageIcon("resource/AnimalIcons/wolfRight.png");
        }else {
            if(owner == PlayerColor.BLUE){
                icon = new ImageIcon("resource/AnimalIcons/wolfL.png");
            }else icon = new ImageIcon("resource/AnimalIcons/wolfR.png");
        }
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
