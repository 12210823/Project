package view;

import view.UI.ImagePanel;
import view.UI.RoundButton;

import javax.swing.*;
import java.awt.*;

public class SettingGameFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    private final int BUTTON_WIDTH = 200;
    private final int BUTTON_HEIGHT = 50;
    private MainGameFrame mainGameFrame;
    private ChessGameFrame chessGameFrame;
    private ImagePanel mainPanel;

    private JLabel mainLabel;
    private RoundButton loginButton;
    private RoundButton rankButton;
    private RoundButton volumeButton;
    private RoundButton themeButton;
    private RoundButton chessboardButton;
    private RoundButton ruleButton;
    private RoundButton backButton;
    public SettingGameFrame(int width, int height, JFrame mainFrame) {
        setTitle("设置"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;

        if (mainFrame instanceof MainGameFrame) {
            this.mainGameFrame = (MainGameFrame) mainFrame;

        } else if (mainFrame instanceof ChessGameFrame) {
            this.chessGameFrame = (ChessGameFrame) mainFrame;

        } else {
            System.out.println("Error: mainFrame is not a MainGameFrame or ChessGameFrame");
        }



        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
//    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        initComponents();
        addBackgroundImage();
        setupLayout();
    }
    public void addBackgroundImage(){
        mainPanel = new ImagePanel("resource/Backgrounds/jungle2.gif");
        setContentPane(mainPanel);
        mainPanel.setLayout(null);
    }
    public void initComponents(){
        addThemeButton();
    }

    public void setupLayout(){
        mainPanel.add(themeButton);
    }
    public void addThemeButton(){
        themeButton = new RoundButton("主题");
        themeButton.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
        themeButton.setFont(new Font("微软雅黑",Font.BOLD,24));

        themeButton.addActionListener(e -> {
            System.out.println("themeButton clicked");
            if (chessGameFrame == null) {
                System.out.println("chessGameFrame is null");
                return;
            }
            // 创建背景选择对话框
            JDialog backgroundDialog = new JDialog(this, "Background", true);
            backgroundDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            backgroundDialog.setSize(300, 200);
            backgroundDialog.setLocationRelativeTo(this);


            // 添加背景选择按钮
            JPanel inputPanel = new JPanel(new GridLayout(4, 1, 10, 10));
            inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JButton springButton = new JButton("春");
            JButton summerButton = new JButton("夏");
            JButton autumnButton = new JButton("秋");
            JButton winterButton = new JButton("冬");
            springButton.addActionListener(e1 -> {
                backgroundDialog.dispose();
                chessGameFrame.setTheme(Theme.spring);
            });
            summerButton.addActionListener(e1 -> {
                backgroundDialog.dispose();
                chessGameFrame.setTheme(Theme.summer);
            });
            autumnButton.addActionListener(e1 -> {
                backgroundDialog.dispose();
                chessGameFrame.setTheme(Theme.autumn);
            });
            winterButton.addActionListener(e1 -> {
                backgroundDialog.dispose();
                chessGameFrame.setTheme(Theme.winter);
            });
            inputPanel.add(springButton);
            inputPanel.add(summerButton);
            inputPanel.add(autumnButton);
            inputPanel.add(winterButton);

            backgroundDialog.add(inputPanel, BorderLayout.CENTER);
            backgroundDialog.setVisible(true);
        });
    }
}
