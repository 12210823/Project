package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends JPanel {
    private ImageIcon background = new ImageIcon();
    private boolean hovered;
    public GridType type;
    public Theme theme;
    public int x;
    public int y;
    private HoverListener hoverListener;
    public CellComponent(GridType type, Point location, int size, Theme theme) {
        this.x = location.x / size;
        this.y = location.y / size;
        this.theme = theme;
        setLayout(new GridLayout(1,1));
        setLocation(location);
        setSize(size, size);
        this.type = type;
        switch (theme) {
            case spring -> spring();
            case summer -> summer();
            case autumn -> autumn();
            case winter -> winter();
        }
        // Add mouse listener to this cell component
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (hoverListener != null) {
                    hoverListener.onHovered(CellComponent.this);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (hoverListener != null) {
                    hoverListener.onExited(CellComponent.this);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Trigger the click event in ChessboardComponent
                getParent().dispatchEvent(new MouseEvent(getParent(), e.getID(), e.getWhen(), e.getModifiersEx(), getLocation().x + e.getX(), getLocation().y + e.getY(), e.getClickCount(), e.isPopupTrigger(), e.getButton()));
            }
        });
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
    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    // Add this setter method to the CellComponent class
    public void setHoverListener(HoverListener hoverListener) {
        this.hoverListener = hoverListener;
    }

    public void setBackground(ImageIcon background) {
        this.background = background;
    }
    public void spring(){
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
    public void summer(){
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
                background = new ImageIcon("resource/CellIcons/summer2.png");
            }else background = new ImageIcon("resource/CellIcons/summer1.png");
        }
    }
    public void autumn(){
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
                background = new ImageIcon("resource/CellIcons/autumn2.png");
            }else background = new ImageIcon("resource/CellIcons/autumn1.png");
        }
    }
    public void winter(){
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
                background = new ImageIcon("resource/CellIcons/winter2.png");
            }else background = new ImageIcon("resource/CellIcons/winter1.png");
        }
    }
}
