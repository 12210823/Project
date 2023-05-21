package view;

import controller.GameController;
import model.Chessboard;
import view.UI.ImagePanel;
import view.UI.RoundButton;

import javax.swing.*;
import java.awt.*;

public class MainGameFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    private ImagePanel mainPanel;
    private JLabel titleLabel;
    private JButton singlePlayerButton;
    private JButton multiPlayerButton;
    private JButton settingsButton;
    private JButton exitButton;

    public MainGameFrame(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;

        initFrame();
        setLayout(null);
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initFrame() {
        setTitle("2023 CS109 Project Demo");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
    }

    private void initComponents() {
        mainPanel = new ImagePanel("resource/Backgrounds/jungle1.gif");
        setContentPane(mainPanel);
        mainPanel.setLayout(new GridBagLayout());

        titleLabel = createTitle("斗兽棋");
        multiPlayerButton = addButton("多人游戏");
        settingsButton = addButton("设置");
        exitButton = addButton("退出");
    }

    private JLabel createTitle(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.white);
        label.setFont(new Font("微软雅黑", Font.BOLD, 64));
        return label;
    }

    private JButton addButton(String text) {
        RoundButton button = new RoundButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        add(button);
        return button;
    }
    private void setupLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add title to the layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);


        // Add buttons to the layout
        addButtonToLayout(multiPlayerButton, gbc, 0, 1);
        addButtonToLayout(settingsButton, gbc, 0, 2);
        addButtonToLayout(exitButton, gbc, 0, 3);

    }

    private void addButtonToLayout(JButton button, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        mainPanel.add(button, gbc);
    }
    private void setupListeners() {
        // Add listeners for buttons

        multiPlayerButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                dispose();
                ChessGameFrame ChessGameFrame = new ChessGameFrame(1300, 800);
                GameController gameController = new GameController(ChessGameFrame.getChessboardComponent(), new Chessboard());
                ChessGameFrame.setVisible(true);
                this.dispose();
            });
        });

        exitButton.addActionListener(e -> System.exit(0));
    }
}




