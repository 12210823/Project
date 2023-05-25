package view;

import controller.GameController;
import model.Chessboard;
import view.UI.ImagePanel;
import view.UI.RoundButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.Serializable;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    private final int ONE_CHESS_SIZE;
    private ImagePanel mainPanel;
    public Theme theme;
    public RoundButton statusLabel;
    private JButton RestartButton;
    public JButton PlaybackButton;
    private JButton SaveButton;
    private JButton LoadButton;
    private JButton SettingButton;
    private JButton ExitButton;
    public JButton ReplayButton;
    public ChessboardComponent chessboardComponent;
    private String[] bgPaths = {
            "resource/Backgrounds/spring.png",
            "resource/Backgrounds/summer.png",
            "resource/Backgrounds/autumn.png",
            "resource/Backgrounds/winter.png"};

    public ChessGameFrame(int width, int height ,int type) {
        setTitle("2023 CS109 Project"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 7;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addBackgroundImage();
        if (type==0)
        {
            initComponents();
        }
        else if (type==1)
        {
            initComponentsAI1();
        }
        else if (type==2)
        {

        }
        else if (type==3)
        {

        }
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
        addRestartButton();
        addReplayButton();
        addPlaybackButton();
    }
    public void initComponentsAI1(){
        addChessboardAI1();
        addLabel();
        addSettingButton();
        addSaveButton();
        addLoadButton();
        addRestartButton();
        addExitButton();
        addRestartButton();
        addReplayButton();
        addPlaybackButton();
    }
    public void setupLayout(){
        SettingButton.setLocation(WIDTH - 290, HEIGTH - 106 - 380);
        mainPanel.add(SettingButton);

        SaveButton.setLocation(WIDTH - 290, HEIGTH - 106 - 300);
        mainPanel.add(SaveButton);

        LoadButton.setLocation(WIDTH - 290, HEIGTH - 106 - 220);
        mainPanel.add(LoadButton);

        RestartButton.setLocation(WIDTH - 290, HEIGTH - 106 - 140);
        mainPanel.add(RestartButton);

        ExitButton.setLocation(WIDTH - 290, HEIGTH - 106 - 60);
        mainPanel.add(ExitButton);

        chessboardComponent.setLocation(100, HEIGTH / 14);
        add(chessboardComponent);

        ReplayButton.setLocation(WIDTH - 290, HEIGTH - 106 - 460);
        mainPanel.add(ReplayButton);

        PlaybackButton.setLocation(WIDTH - 290, HEIGTH - 106 - 540);
        mainPanel.add(PlaybackButton);
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
        new GameController(chessboardComponent, new Chessboard(),0);
        chessboardComponent.setChessGameFrame(this);
    }

    private void addChessboardAI1() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        new GameController(chessboardComponent, new Chessboard(),1);
        chessboardComponent.setChessGameFrame(this);
    }
    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        statusLabel = new RoundButton("第1回合，左方行棋");
        statusLabel.setLocation(WIDTH-340, 0);
        statusLabel.setSize(300, 50);
        statusLabel.setForeground(Color.white);
        statusLabel.setBorder(BorderFactory.createEmptyBorder());
        statusLabel.setFont(new Font("微软雅黑", Font.BOLD, 22));
        add(statusLabel);
    }

    private void addSaveButton() {
        SaveButton = new RoundButton("存档");
        SaveButton.addActionListener((e) -> {
            JFileChooser fd = new JFileChooser();
            //fd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fd.showOpenDialog(null);
            File f = fd.getSelectedFile();
            if(f != null){}
            chessboardComponent.getGameController().saveGameToFile(f.getPath());
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
            JFileChooser fd = new JFileChooser();
            //fd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fd.showOpenDialog(null);
            File f = fd.getSelectedFile();
            if(f != null){}
            chessboardComponent.getGameController().loadGameFromFile(f.getPath());
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
    private void addReplayButton(){
        ReplayButton = new RoundButton("悔棋");
        ReplayButton.setSize(200, 60);
        ReplayButton.setBorder(BorderFactory.createEmptyBorder());
        ReplayButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        ReplayButton.addActionListener(e -> {
            chessboardComponent.getGameController().Replay();
        });
    }
    private void addPlaybackButton(){
        PlaybackButton = new RoundButton("回放");
        PlaybackButton.setSize(200, 60);
        PlaybackButton.setBorder(BorderFactory.createEmptyBorder());
        PlaybackButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        PlaybackButton.addActionListener(e -> {
            chessboardComponent.getGameController().Playback();
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
