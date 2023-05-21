package view;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends JPanel {
    private ImageIcon background = new ImageIcon();

    public CellComponent(GridType type, Point location, int size) {
        int x = location.x / size;
        int y = location.y / size;
        setLayout(new GridLayout(1,1));
        setLocation(location);
        setSize(size, size);
        if(type == GridType.RIVER){
            if((x + y) % 2 != 0){
                background = new ImageIcon("resource/CellIcons/water1.png");
            }else background = new ImageIcon("resource/CellIcons/water2.png");
        } else if (type == GridType.DENS) {
            if(x == 0){
                background = new ImageIcon("resource/CellIcons/caveLeft.png");
            }else background = new ImageIcon("resource/CellIcons/caveRight.png");
        } else if (type == GridType.TRAP) {
            background = new ImageIcon("resource/CellIcons/grassWithTrap.png");
        } else {
            if((x + y) % 2 == 0){
                background = new ImageIcon("resource/CellIcons/grass2.png");
            }else background = new ImageIcon("resource/CellIcons/grass1.png");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(background != null){
            g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}
