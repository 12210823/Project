package view;

import music.MusicThread;
import view.UI.ImagePanel;
import view.UI.RoundButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class MainGameFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    private ImagePanel mainPanel;
    private JLabel titleLabel;
    private JButton singlePlayerButton;
    private JButton multiPlayerButton;
    private JButton settingButton;
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
        setTitle("斗兽棋");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
    }

    private void initComponents() {
        mainPanel = new ImagePanel("resource/Backgrounds/jungle1.gif");
        setContentPane(mainPanel);
        mainPanel.setLayout(new GridBagLayout());

        titleLabel = createTitle();
        singlePlayerButton= addButton("单人游戏");
        multiPlayerButton = addButton("多人游戏");
        settingButton = addButton("设置");
        exitButton = addButton("退出");
    }

    private JLabel createTitle() {
        JLabel label = new JLabel("斗兽棋");
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
        gbc.insets = new Insets(4, 5, 5, 5);

        // Add title to the layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);


        // Add buttons to the layout
        addButtonToLayout(singlePlayerButton,gbc,0,1);
        addButtonToLayout(multiPlayerButton, gbc, 0, 2);
        addButtonToLayout(settingButton, gbc, 0, 3);
        addButtonToLayout(exitButton, gbc, 0, 4);

    }

    private void addButtonToLayout(JButton button, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        mainPanel.add(button, gbc);
    }
    private void setupListeners() {
        // Add listeners for buttons

        singlePlayerButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            click();
            JDialog backgroundDialog = new JDialog(this, "模式选择", true);
            backgroundDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            backgroundDialog.setSize(300, 225);
            backgroundDialog.setLocationRelativeTo(this);
            JPanel inputPanel = new ImagePanel("resource/Backgrounds/jungle3.gif");
            inputPanel.setLayout(new GridLayout(3, 1, 10, 10));
            inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 70, 10, 70));
            JButton mode1 = new RoundButton("简单模式");
            JButton mode2 = new RoundButton("中等模式");
            JButton mode3 = new RoundButton("困难模式");

            mode1.addActionListener(e1 -> {
                click();
                dispose();
                ChessGameFrame chessGameFrame = new ChessGameFrame(1200, 800,1);
                //GameController gameController = new GameController(chessGameFrame.getChessboardComponent(), new Chessboard());
                chessGameFrame.setVisible(true);
                this.dispose();
            });

            mode2.addActionListener(e1 -> {
                click();
                dispose();
                ChessGameFrame chessGameFrame = new ChessGameFrame(1200, 800,2);
                //GameController gameController = new GameController(chessGameFrame.getChessboardComponent(), new Chessboard());
                chessGameFrame.setVisible(true);
                this.dispose();
            });

            mode3.addActionListener(e1 -> {
                click();
                dispose();
                ChessGameFrame chessGameFrame = new ChessGameFrame(1200, 800,3);
                //GameController gameController = new GameController(chessGameFrame.getChessboardComponent(), new Chessboard());
                chessGameFrame.setVisible(true);
                this.dispose();
            });

            mode1.setFont(new Font("微软雅黑",Font.PLAIN,18));
            mode2.setFont(new Font("微软雅黑",Font.PLAIN,18));
            mode3.setFont(new Font("微软雅黑",Font.PLAIN,18));
            inputPanel.add(mode1);
            inputPanel.add(mode2);
            inputPanel.add(mode3);

            backgroundDialog.add(inputPanel, BorderLayout.CENTER);
            backgroundDialog.setVisible(true);
        }));
        multiPlayerButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            dispose();
            ChessGameFrame chessGameFrame = new ChessGameFrame(1200, 800,0);
            //GameController gameController = new GameController(chessGameFrame.getChessboardComponent(), new Chessboard());
            chessGameFrame.setVisible(true);
            this.dispose();
        }));
        settingButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            click();
            SettingGameFrame SettingGameFrame = new SettingGameFrame(500, 300, this);
            SettingGameFrame.setVisible(true);
        }));
        exitButton.addActionListener(e -> {
            click();
            System.exit(0);
        });
    }
    public void click(){
        File musicFile = new File("resource/Music/click.wav");

        URL musicURL;
        try {
            musicURL = musicFile.toURI().toURL();
        } catch (MalformedURLException e2) {
            throw new RuntimeException(e2);
        }
        // 创建音乐线程实例
        MusicThread musicThread = new MusicThread(musicURL, false);

        // 创建线程并启动
        Thread music = new Thread(musicThread);
        music.start();
        musicThread.setVolume(0.5f);
    }
}




