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
    private int rank;
    public ChessComponent(PlayerColor owner, int size) {
        this.owner = owner;
        this.selected = false;
        setSize(size/2, size/2);
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
            g.drawOval(1, 1, getWidth() - 1, getHeight() - 1);
            g.drawOval(2, 2, getWidth() - 2, getHeight() - 2);
        }
    }
}
