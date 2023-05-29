package view;


import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class ChessComponent extends JComponent {
    private PlayerColor owner;
    private boolean selected;
    private int style;
    public ChessComponent(PlayerColor owner, int size,int style) {
        this.owner = owner;
        this.selected = false;
        this.style = style;
        setSize(size, size);
        setLocation(0,0);
        setVisible(true);
    }
    public ChessComponent(PlayerColor owner, int size) {
        this.owner = owner;
        this.selected = false;
        this.style = 0;
        setSize(size, size);
        setLocation(0,0);
        setVisible(true);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.BLACK);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}
