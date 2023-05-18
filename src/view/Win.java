package view;

import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

public class Win extends JFrame {
    private JLabel label;

    public Win(PlayerColor a) {
        super("Game Over");
        setLayout(new FlowLayout());
        label = new JLabel(a.toString()+" win!");
        label.setFont(new Font("San Serif", Font.PLAIN, 30));
        add(label);
    }
}
