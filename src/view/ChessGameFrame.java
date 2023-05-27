package view;

import controller.GameController;
import model.Chessboard;
import music.MusicThread;
import view.UI.ImagePanel;
import view.UI.RoundButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

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
        initComponents(type);
        setupLayout();
    }
    private void addBackgroundImage() {
        String defaultPath = bgPaths[0];
        mainPanel = new ImagePanel(defaultPath);
        setContentPane(mainPanel);
        mainPanel.setLayout(null);
    }
    public void initComponents(int x){
        addChessboard(x);
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
        SettingButton.setLocation(WIDTH - 220, HEIGTH - 76 - 300);
        mainPanel.add(SettingButton);

        SaveButton.setLocation(WIDTH - 220, HEIGTH - 76 - 220);
        mainPanel.add(SaveButton);

        LoadButton.setLocation(WIDTH - 140, HEIGTH - 76 - 220);
        mainPanel.add(LoadButton);

        RestartButton.setLocation(WIDTH - 220, HEIGTH - 76 - 140);
        mainPanel.add(RestartButton);

        ExitButton.setLocation(WIDTH - 220, HEIGTH - 76 - 60);
        mainPanel.add(ExitButton);

        chessboardComponent.setLocation(90, HEIGTH / 14 + 30);
        add(chessboardComponent);

        ReplayButton.setLocation(WIDTH - 220, HEIGTH - 76 - 380);
        mainPanel.add(ReplayButton);

        PlaybackButton.setLocation(WIDTH - 220, HEIGTH - 76 - 460);
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
    private void addChessboard(int x) {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        new GameController(chessboardComponent, new Chessboard(),x);
        chessboardComponent.setChessGameFrame(this);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        statusLabel = new RoundButton("第1回合，左方行棋");
        statusLabel.setLocation(WIDTH / 2 - 125, 0);
        statusLabel.setSize(270, 60);
        statusLabel.setForeground(Color.white);
        statusLabel.setBorder(BorderFactory.createEmptyBorder());
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 25));
        add(statusLabel);
    }

    private void addSaveButton() {
        SaveButton = new RoundButton("存");
        SaveButton.addActionListener((e) -> {
            File musicFile = new File("resource/Music/click.wav");

            URL musicURL;
            try {
                musicURL = musicFile.toURI().toURL();
            } catch (MalformedURLException e1) {
                throw new RuntimeException(e1);
            }
            // 创建音乐线程实例
            MusicThread musicThread = new MusicThread(musicURL, false);

            // 创建线程并启动
            Thread music = new Thread(musicThread);
            music.start();
            musicThread.setVolume(0.5f);
            JFileChooser fd = new JFileChooser();
            //fd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fd.showOpenDialog(null);
            File f = fd.getSelectedFile();
            if(f != null){}
            chessboardComponent.getGameController().saveGameToFile(f.getPath());
        });
        SaveButton.setBorder(BorderFactory.createEmptyBorder());
        SaveButton.setSize(60, 60);
        SaveButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));

    }

    private void addLoadButton() {
        LoadButton = new RoundButton("读");
        LoadButton.setForeground(Color.white);

        LoadButton.setSize(60, 60);
        LoadButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
        LoadButton.setBorder(BorderFactory.createEmptyBorder());

        LoadButton.addActionListener(e -> {
            File musicFile = new File("resource/Music/click.wav");

            URL musicURL;
            try {
                musicURL = musicFile.toURI().toURL();
            } catch (MalformedURLException e1) {
                throw new RuntimeException(e1);
            }
            // 创建音乐线程实例
            MusicThread musicThread = new MusicThread(musicURL, false);

            // 创建线程并启动
            Thread music = new Thread(musicThread);
            music.start();
            musicThread.setVolume(0.5f);
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
        RestartButton.addActionListener((e) -> chessboardComponent.getGameController().Restart());

        RestartButton.setSize(140, 60);
        RestartButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
        RestartButton.setBorder(BorderFactory.createEmptyBorder());

    }
    private void addExitButton() {
        ExitButton = new RoundButton("退出");
        ExitButton.setSize(140, 60);
        ExitButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
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
        SettingButton.setSize(140, 60);
        SettingButton.setBorder(BorderFactory.createEmptyBorder());
        SettingButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
        SettingButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            SettingGameFrame SettingGameFrame = new SettingGameFrame(500, 300, this);
            SettingGameFrame.setVisible(true);
        }));
    }
    private void addReplayButton(){
        ReplayButton = new RoundButton("悔棋");
        ReplayButton.setSize(140, 60);
        ReplayButton.setBorder(BorderFactory.createEmptyBorder());
        ReplayButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
        ReplayButton.addActionListener(e -> chessboardComponent.getGameController().Replay());
    }
    private void addPlaybackButton(){
        PlaybackButton = new RoundButton("回放");
        PlaybackButton.setSize(140, 60);
        PlaybackButton.setBorder(BorderFactory.createEmptyBorder());
        PlaybackButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
        PlaybackButton.addActionListener(e -> chessboardComponent.getGameController().Playback());
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
