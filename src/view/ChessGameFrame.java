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
    public int style;
    public RoundButton statusLabel;
    private JButton RestartButton;
    public JButton PlaybackButton;
    private JButton SaveButton;
    private JButton LoadButton;
    private JButton SettingButton;
    private JButton ExitButton;
    public JButton ReplayButton;
    private JButton MenuButton;

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
        addMenuButton();
    }
    public void setupLayout(){
        ExitButton.setLocation(WIDTH - 79 - 140 - 5, HEIGTH - 76 - 60);
        mainPanel.add(ExitButton);

        chessboardComponent.setLocation(79, HEIGTH / 14 + 30);
        add(chessboardComponent);

        ReplayButton.setLocation(WIDTH - 79 - 140 - 5, HEIGTH - 76 - 220);
        mainPanel.add(ReplayButton);

        MenuButton.setLocation(WIDTH - 79 - 140 - 5,HEIGTH - 76 - 140);
        mainPanel.add(MenuButton);
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
    private void addMenuButton(){
        MenuButton = new RoundButton("菜单");
        MenuButton.setBorder(BorderFactory.createEmptyBorder());
        MenuButton.setSize(140, 60);
        MenuButton.setFont(new Font("微软雅黑", Font.BOLD, 25));

        MenuButton.addActionListener(e -> {
            File musicFile = new File("resource/Music/click.wav");

            URL musicURL;
            try {
                musicURL = musicFile.toURI().toURL();
            } catch (MalformedURLException e1) {
                throw new RuntimeException(e1);
            }
            MusicThread musicThread = new MusicThread("resource/Music/click.wav", false);

            Thread music = new Thread(musicThread);
            music.start();

            JDialog menu = new JDialog(this,"菜单",true);
            menu.setSize(400,300);
            menu.setLocationRelativeTo(this);

            JPanel backGround = new ImagePanel("resource/Backgrounds/jungle3.gif");
            backGround.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
            backGround.setLayout(new GridLayout(3, 2, 20, 15));

            JButton back = new RoundButton("返回");
            back.setFont(new Font("微软雅黑", Font.BOLD, 25));
            back.setBorder(BorderFactory.createEmptyBorder());
            back.addActionListener(e1 -> menu.dispose());
            SettingButton.addActionListener(e1 -> menu.dispose());
            RestartButton.addActionListener(e1 -> menu.dispose());
            SaveButton.addActionListener(e1 -> menu.dispose());
            LoadButton.addActionListener(e1 -> menu.dispose());
            PlaybackButton.addActionListener(e1 -> menu.dispose());

            backGround.add(SettingButton);
            backGround.add(RestartButton);
            backGround.add(SaveButton);
            backGround.add(LoadButton);
            backGround.add(PlaybackButton);
            backGround.add(back);

            menu.add(backGround);

            menu.setVisible(true);
        });
    }
    private void addLabel() {
        statusLabel = new RoundButton("第1回合，左方行棋");
        statusLabel.setLocation(WIDTH - 289, 0);
        statusLabel.setSize(270, 60);
        statusLabel.setForeground(Color.white);
        statusLabel.setBorder(BorderFactory.createEmptyBorder());
        statusLabel.setFont(new Font("微软雅黑", Font.BOLD, 25));
        add(statusLabel);
    }

    private void addSaveButton() {
        SaveButton = new RoundButton("存档");
        SaveButton.addActionListener((e) -> {
            File musicFile = new File("resource/Music/click.wav");

            URL musicURL;
            try {
                musicURL = musicFile.toURI().toURL();
            } catch (MalformedURLException e1) {
                throw new RuntimeException(e1);
            }
            // 创建音乐线程实例
            MusicThread musicThread = new MusicThread("resource/Music/click.wav", false);

            // 创建线程并启动
            Thread music = new Thread(musicThread);
            music.start();
            JFileChooser fd = new JFileChooser();
            //fd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fd.showOpenDialog(null);
            File f = fd.getSelectedFile();
            if(f != null){}
            chessboardComponent.getGameController().saveGameToFile(f.getPath());
        });
        SaveButton.setBorder(BorderFactory.createEmptyBorder());
        SaveButton.setSize(60, 60);
        SaveButton.setFont(new Font("微软雅黑", Font.BOLD, 25));

    }

    private void addLoadButton() {
        LoadButton = new RoundButton("读档");
        LoadButton.setForeground(Color.white);

        LoadButton.setSize(60, 60);
        LoadButton.setFont(new Font("微软雅黑", Font.BOLD, 25));
        LoadButton.setBorder(BorderFactory.createEmptyBorder());

        LoadButton.addActionListener(e -> {

            MusicThread musicThread = new MusicThread("resource/Music/click.wav", false);

            Thread music = new Thread(musicThread);
            music.start();
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
        RestartButton.setFont(new Font("微软雅黑", Font.BOLD, 25));
        RestartButton.setBorder(BorderFactory.createEmptyBorder());

    }
    private void addExitButton() {
        ExitButton = new RoundButton("退出");
        ExitButton.setSize(140, 60);
        ExitButton.setFont(new Font("微软雅黑", Font.BOLD, 25));
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
        SettingButton.setFont(new Font("微软雅黑", Font.BOLD, 25));
        SettingButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            SettingGameFrame SettingGameFrame = new SettingGameFrame(500, 300, this);
            SettingGameFrame.setVisible(true);
        }));
    }
    private void addReplayButton(){
        ReplayButton = new RoundButton("悔棋");
        ReplayButton.setSize(140, 60);
        ReplayButton.setBorder(BorderFactory.createEmptyBorder());
        ReplayButton.setFont(new Font("微软雅黑", Font.BOLD, 25));
        ReplayButton.addActionListener(e -> chessboardComponent.getGameController().Replay());
    }
    private void addPlaybackButton(){
        PlaybackButton = new RoundButton("回放");
        PlaybackButton.setSize(140, 60);
        PlaybackButton.setBorder(BorderFactory.createEmptyBorder());
        PlaybackButton.setFont(new Font("微软雅黑", Font.BOLD, 25));
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

    public void setStyle(int style) {
        this.style = style;
        chessboardComponent.setStyle(style);
    }
}
