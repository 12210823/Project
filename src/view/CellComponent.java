package view;

import model.ChessboardPoint;

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
    private boolean selected;
    private boolean hovered;
    public GridType type;
    public Theme theme;
    public int x;
    public int y;

    public ChessboardPoint point;
    private HoverListener hoverListener;
    public CellComponent(GridType type, Point location, int size, Theme theme) {
        this.x = location.x / size;
        this.y = location.y / size;

        this.theme = theme;
        setLayout(new GridLayout(1,1));
        setLocation(location);
        setSize(size, size);
        this.type = type;
        unselected();
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
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(selected){
            selected();
        }else if(hovered){
            selected();
        } else if (!hovered) {
            unselected();
        } else unselected();
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
    public void selected(){

            if (theme.equals(Theme.spring)||theme.equals(Theme.summer)){
                    if(type.equals(GridType.RIVER) )  setBackground(new ImageIcon(getClass().getResource("/CellIcons/waterEntered.png")));
                if(type.equals(GridType.LAND) ) setBackground(new ImageIcon(getClass().getResource("/CellIcons/grassEntered.png")));
                if(type.equals(GridType.TRAP) )setBackground(new ImageIcon(getClass().getResource("/CellIcons/trapEntered.png")));
                if(type.equals(GridType.DENS) )
                        if(x == 0){
                            setBackground(new ImageIcon(getClass().getResource("/CellIcons/caveLeftEntered.png")));
                        } else setBackground(new ImageIcon(getClass().getResource("/CellIcons/caveRightEntered.png")));


            }
        if (theme.equals(Theme.autumn)) {
            if(type.equals(GridType.RIVER) ) setBackground(new ImageIcon(getClass().getResource("/CellIcons/waterEntered.png")));
            if(type.equals(GridType.LAND)) setBackground(new ImageIcon(getClass().getResource("/CellIcons/autumnEntered.png")));
            if(type.equals(GridType.TRAP) ) setBackground(new ImageIcon(getClass().getResource("/CellIcons/trapAutumnEntered.png")));
            if(type.equals(GridType.DENS) ) {
                        if(x == 0){
                            setBackground(new ImageIcon(getClass().getResource("/CellIcons/leftAutumnEntered.png")));
                        } else setBackground(new ImageIcon(getClass().getResource("/CellIcons/rightAutumnEntered.png")));
                    }
            }
        if (theme.equals(Theme.winter)) {

                    if(type.equals(GridType.RIVER) ) setBackground(new ImageIcon(getClass().getResource("/CellIcons/waterEntered.png")));
                    if(type.equals(GridType.LAND)) setBackground(new ImageIcon(getClass().getResource("/CellIcons/winterEntered.png")));
                    if(type.equals(GridType.TRAP) ) setBackground(new ImageIcon(getClass().getResource("/CellIcons/trapWinterEntered.png")));
                    if(type.equals(GridType.DENS) ) {
                        if(x == 0){
                            setBackground(new ImageIcon(getClass().getResource("/CellIcons/leftWinterEntered.png")));
                        } else setBackground(new ImageIcon(getClass().getResource("/CellIcons/rightWinterEntered.png")));
                    }

            }

    }
    public void unselected(){
        if (theme.equals(Theme.spring))
            spring();
           else if (theme.equals(Theme.summer))
             summer();
           else if (theme.equals(Theme.autumn))
             autumn();
            else winter();

    }
    public void spring(){
        if(type == GridType.RIVER){
            if((x + y) % 2 != 0){
                background = new ImageIcon(getClass().getResource("/CellIcons/water1.png"));
            }else background = new ImageIcon(getClass().getResource("/CellIcons/water2.png"));
        } else if (type == GridType.DENS) {
            if(x == 0){
                background = new ImageIcon(getClass().getResource("/CellIcons/caveLeft.png"));
            }else background = new ImageIcon(getClass().getResource("/CellIcons/caveRight.png"));
        } else if (type == GridType.TRAP) {
            background = new ImageIcon(getClass().getResource("/CellIcons/grassWithTrap.png"));
        } else {
            if((x + y) % 2 == 0){
                background = new ImageIcon(getClass().getResource("/CellIcons/grass2.png"));
            }else background = new ImageIcon(getClass().getResource("/CellIcons/grass1.png"));
        }
    }
    public void summer(){
        if(type == GridType.RIVER){
            if((x + y) % 2 != 0){
                background = new ImageIcon(getClass().getResource("/CellIcons/water1.png"));
            }else background = new ImageIcon(getClass().getResource("/CellIcons/water2.png"));
        } else if (type == GridType.DENS) {
            if(x == 0){
                background = new ImageIcon(getClass().getResource("/CellIcons/leftSummer.png"));
            }else background = new ImageIcon(getClass().getResource("/CellIcons/rightSummer.png"));
        } else if (type == GridType.TRAP) {
            background = new ImageIcon(getClass().getResource("/CellIcons/trapSummer.png"));
        } else {
            if((x + y) % 2 == 0){
                background = new ImageIcon(getClass().getResource("/CellIcons/summer2.png"));
            }else background = new ImageIcon(getClass().getResource("/CellIcons/summer1.png"));
        }
    }
    public void autumn(){
        if(type == GridType.RIVER){
            if((x + y) % 2 != 0){
                background = new ImageIcon(getClass().getResource("/CellIcons/water1.png"));
            }else background = new ImageIcon(getClass().getResource("/CellIcons/water2.png"));
        } else if (type == GridType.DENS) {
            if(x == 0){
                background = new ImageIcon(getClass().getResource("/CellIcons/leftAutumn.png"));
            }else background = new ImageIcon(getClass().getResource("/CellIcons/rightAutumn.png"));
        } else if (type == GridType.TRAP) {
            background = new ImageIcon(getClass().getResource("/CellIcons/trapAutumn.png"));
        } else {
            if((x + y) % 2 == 0){
                background = new ImageIcon(getClass().getResource("/CellIcons/autumn2.png"));
            }else background = new ImageIcon(getClass().getResource("/CellIcons/autumn1.png"));
        }
    }
    public void winter(){
        if(type == GridType.RIVER){
            if((x + y) % 2 != 0){
                background = new ImageIcon(getClass().getResource("/CellIcons/water1.png"));
            }else background = new ImageIcon(getClass().getResource("/CellIcons/water2.png"));
        } else if (type == GridType.DENS) {
            if(x == 0){
                background = new ImageIcon(getClass().getResource("/CellIcons/leftWinter.png"));
            }else background = new ImageIcon(getClass().getResource("/CellIcons/rightWinter.png"));
        } else if (type == GridType.TRAP) {
            background = new ImageIcon(getClass().getResource("/CellIcons/trapWinter.png"));
        } else {
            if((x + y) % 2 == 0){
                background = new ImageIcon(getClass().getResource("/CellIcons/winter2.png"));
            }else background = new ImageIcon(getClass().getResource("/CellIcons/winter1.png"));
        }
    }
    /*public void warn(){
        switch (theme){
            case spring, summer, winter -> {
                switch (type){
                    case RIVER -> setBackground(new ImageIcon(getClass().getResource("/CellIcons/waterEntered.png")));
                    case LAND -> setBackground(new ImageIcon(getClass().getResource("/CellIcons/autumnEntered.png")));
                    case TRAP -> setBackground(new ImageIcon(getClass().getResource("/CellIcons/trapAutumnEntered.png")));
                    case DENS -> {
                        if(x == 0){
                            setBackground(new ImageIcon(getClass().getResource("/CellIcons/leftAutumnEntered.png")));
                        } else setBackground(new ImageIcon(getClass().getResource("/CellIcons/rightAutumnEntered.png")));
                    }
                }
            }
            case autumn -> {
                switch (type){
                    case RIVER -> setBackground(new ImageIcon(getClass().getResource("/CellIcons/waterEntered.png")));
                    case LAND -> setBackground(new ImageIcon(getClass().getResource("/CellIcons/grassEntered.png")));
                    case TRAP -> setBackground(new ImageIcon(getClass().getResource("/CellIcons/trapEntered.png")));
                    case DENS -> {
                        if(x == 0){
                            setBackground(new ImageIcon(getClass().getResource("/CellIcons/caveLeftEntered.png")));
                        } else setBackground(new ImageIcon(getClass().getResource("/CellIcons/caveRightEntered.png")));
                    }
                }
            }
        }
    }*/
}
