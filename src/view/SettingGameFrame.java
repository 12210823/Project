package view;

import view.UI.ImagePanel;
import view.UI.RoundButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        addRuleButton();
        addBackButton();
    }

    public void setupLayout(){
        mainPanel.add(themeButton);
        themeButton.setLocation(getWidth() / 2 - BUTTON_WIDTH / 2, 10);
        mainPanel.add(ruleButton);
        ruleButton.setLocation(getWidth() / 2 - BUTTON_WIDTH / 2, BUTTON_HEIGHT + 20);
        mainPanel.add(backButton);
        backButton.setLocation(getWidth() / 2 - BUTTON_WIDTH / 2, 2 * BUTTON_HEIGHT + 30);
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
            backgroundDialog.setSize(300, 225);
            backgroundDialog.setLocationRelativeTo(this);


            // 添加背景选择按钮
            JPanel inputPanel = new ImagePanel("resource/Backgrounds/jungle3.gif");
            inputPanel.setLayout(new GridLayout(4, 1, 10, 10));
            inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 70, 10, 70));
            JButton springButton = new RoundButton("春");
            JButton summerButton = new RoundButton("夏");
            JButton autumnButton = new RoundButton("秋");
            JButton winterButton = new RoundButton("冬");
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
            springButton.setFont(new Font("微软雅黑",Font.PLAIN,18));
            summerButton.setFont(new Font("微软雅黑",Font.PLAIN,18));
            autumnButton.setFont(new Font("微软雅黑",Font.PLAIN,18));
            winterButton.setFont(new Font("微软雅黑",Font.PLAIN,18));
            inputPanel.add(springButton);
            inputPanel.add(summerButton);
            inputPanel.add(autumnButton);
            inputPanel.add(winterButton);

            backgroundDialog.add(inputPanel, BorderLayout.CENTER);
            backgroundDialog.setVisible(true);
        });
    }
    private void addRuleButton() {
        ruleButton = new RoundButton("规则");
        ruleButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        ruleButton.setFont(new Font("微软雅黑", Font.BOLD, 24));

        // Add an ActionListener to the ruleButton
        ruleButton.addActionListener(e -> showGameRules());
    }
    public void showGameRules(){
        String gameRule = "斗兽棋是中国一种棋类游戏，双方有八只棋子，依大小顺序为象、狮、虎、豹、犬、狼、猫、鼠。较大的可吃较小的，同类可以互吃，而鼠则可吃象，象不能吃鼠。动物走一格，前后左右都可以。\n\n棋盘横七列，纵九行。棋子放在格子中。双方底在线各有三个陷阱（作品字排）和一个兽穴(于品字中间)。如果一方进入了对方的兽穴便胜出。任何一方都不能进入自己的兽穴。如果对方的兽类走进陷阱，己方任何一只兽都可以把它吃掉，如果敌兽进入陷阱，一回合后，自己的兽类不吃掉陷阱中的敌兽，当对方进入己方兽穴时，则本方输。中间有两条小河（跟湖差不多）。狮、虎可以横直方向跳过河，而且可以直接把对岸的动物吃掉。只有鼠可以下水，在水中的鼠可以阻隔狮、虎跳河。两鼠在水内可以互吃。水里的不可直接吃陆上的,陆上的也不可吃水里的。";
        JTextArea textArea = new JTextArea(gameRule);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setMargin(new Insets(10, 10, 10, 10));
        textArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(
                null,
                scrollPane,
                "游戏规则",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    private void addBackButton() {
        backButton = new RoundButton("返回");
        backButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        backButton.setFont(new Font("微软雅黑", Font.BOLD, 24));
        backButton.addActionListener(e -> {
            dispose();
        });
    }

}
