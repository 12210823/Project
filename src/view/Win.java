package view;

import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

public class Win extends JFrame {
    private JLabel label;

    public Win(PlayerColor a) {
        super("游戏结束");
        setLayout(new FlowLayout());
        if(a == PlayerColor.BLUE){
            label = new JLabel("左方胜利");
        }else label = new JLabel("右方胜利");

        label.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        add(label);
    }
}
