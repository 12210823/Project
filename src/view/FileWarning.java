package view;

import javax.swing.*;
import java.awt.*;

public class FileWarning extends JFrame {
    private JLabel label;

    public FileWarning() {
        super("Game Over");
        setLayout(new FlowLayout());
        label = new JLabel("检测到非法修改文档！\n已从新开始。");
        label.setFont(new Font("San Serif", Font.PLAIN, 30));
        add(label);
    }

}
