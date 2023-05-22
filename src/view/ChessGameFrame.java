package view;

import view.UI.ImagePanel;
import view.UI.RoundButton;

import javax.swing.*;
import java.awt.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    private final int ONE_CHESS_SIZE;
    private ImagePanel mainPanel;
    private Theme theme;
    private JLabel statusLabel;
    private JButton RestartButton;
    private JButton UndoButton;
    private JButton SaveButton;
    private JButton LoadButton;
    private JButton SettingButton;
    private JButton ExitButton;
    private ChessboardComponent chessboardComponent;
    private String[] bgPaths = {
            "resource/Backgrounds/spring.png",
            "resource/Backgrounds/summer.png",
            "resource/Backgrounds/autumn.png",
            "resource/Backgrounds/winter.png"};

    public ChessGameFrame(int width, int height) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 7;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addBackgroundImage();
        initComponents();
        setupLayout();
    }
    private void addBackgroundImage() {
        String defaultPath = bgPaths[0];
        mainPanel = new ImagePanel(defaultPath);
        setContentPane(mainPanel);
        mainPanel.setLayout(null);
    }
    public void initComponents(){
        addChessboard();
        addLabel();
        addSettingButton();
        addSaveButton();
        addLoadButton();
        addRestartButton();
        addExitButton();
    }
    public void setupLayout(){
        SettingButton.setLocation(WIDTH - 290, HEIGTH - 106 - 460);
        mainPanel.add(SettingButton);

        SaveButton.setLocation(WIDTH - 290, HEIGTH - 106 - 360);
        mainPanel.add(SaveButton);

        LoadButton.setLocation(WIDTH - 290, HEIGTH - 106 - 260);
        mainPanel.add(LoadButton);

        RestartButton.setLocation(WIDTH - 290, HEIGTH - 106 - 160);
        mainPanel.add(RestartButton);

        ExitButton.setLocation(WIDTH - 290, HEIGTH - 106 - 60);
        mainPanel.add(ExitButton);

        chessboardComponent.setLocation(100, HEIGTH / 14);
        add(chessboardComponent);
    }
    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);


    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("Sample label");
        statusLabel.setLocation(HEIGTH + 90, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addSaveButton() {
        SaveButton = new RoundButton("存档");
        SaveButton.addActionListener((e) -> {
            System.out.println("Click save");
            String path = JOptionPane.showInputDialog(this,"Input Path here");
            chessboardComponent.getGameController().saveGameToFile(path);
        });
        SaveButton.setBorder(BorderFactory.createEmptyBorder());
        SaveButton.setSize(200, 60);
        SaveButton.setFont(new Font("微软雅黑", Font.BOLD, 20));

    }

    private void addLoadButton() {
        LoadButton = new RoundButton("读档");
        LoadButton.setForeground(Color.white);

        LoadButton.setSize(200, 60);
        LoadButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        LoadButton.setBorder(BorderFactory.createEmptyBorder());

        LoadButton.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this,"Input Path here");
            chessboardComponent.getGameController().loadGameFromFile(path);
        });
    }

    private void addRestartButton() {
        RestartButton = new RoundButton("重置");
        RestartButton.addActionListener((e) -> {
            chessboardComponent.getGameController().Restart();
        });

        RestartButton.setSize(200, 60);
        RestartButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        RestartButton.setBorder(BorderFactory.createEmptyBorder());

    }
    private void addExitButton() {
        ExitButton = new RoundButton("退出");
        ExitButton.setSize(200, 60);
        ExitButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        ExitButton.setBorder(BorderFactory.createEmptyBorder());
        ExitButton.addActionListener(e -> {
            System.out.println("Click exit");
            SwingUtilities.invokeLater(() -> {
                dispose();
                MainGameFrame mainGameFrame = new MainGameFrame(800, 500);
                mainGameFrame.setVisible(true);
            });
        });

    }
    private void addSettingButton(){
        SettingButton = new RoundButton("设置");
        SettingButton.setSize(200, 60);
        SettingButton.setBorder(BorderFactory.createEmptyBorder());
        SettingButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        SettingButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                SettingGameFrame SettingGameFrame = new SettingGameFrame(500, 300, this);
                SettingGameFrame.setVisible(true);
            });
        });
    }
    public void setTheme(Theme theme) {
        this.theme = theme;
        switch (this.theme){
            case spring -> {
                mainPanel.setBackgroundImage(bgPaths[0]);
                chessboardComponent.setTheme(Theme.spring);
            }
            case summer -> {
                mainPanel.setBackgroundImage(bgPaths[1]);
                chessboardComponent.setTheme(Theme.summer);
            }
            case autumn -> {
                mainPanel.setBackgroundImage(bgPaths[2]);
                chessboardComponent.setTheme(Theme.autumn);
            }
            case winter -> {
                mainPanel.setBackgroundImage(bgPaths[3]);
                chessboardComponent.setTheme(Theme.winter);
            }
        }
        mainPanel.repaint();
        chessboardComponent.repaint();
    }
}
