package view;

import controller.GameController;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;
    private static final int NUM_STEPS = 12; // 渐变的步数
    private static final int DELAY = 1; //每一步的延迟时间（以毫秒为单位）

    private static int currentStep = 0; // 当前步数
    private static Timer timer; // 定时器
    private ChessboardComponent chessboardComponent;
    public ChessGameFrame(int width, int height) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        addLabel();
        addHelloButton();
        addLoadButton();
        addRestartButton();
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
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
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

    private void addHelloButton() {
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(HEIGTH + 90, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setBackground(new Color(139, 69, 19));
        button.setForeground(Color.white);
        button.setLocation(HEIGTH + 90, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(100, 40));
        add(button);
        // 鼠标悬停事件监听器
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startColorTransition(button, new Color(49, 36, 1));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startColorTransition(button, new Color(139, 69, 19));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                button.setBackground(Color.GREEN); // 鼠标点击时颜色变为绿色
            }
        });
        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this,"Input Path here");
            //gameController.loadGameFromFile(path);
        });
    }

    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.addActionListener((e) -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1200, 810);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
            mainFrame.setVisible(true);
        });
        button.setLocation(HEIGTH + 90, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(100, 40));
        add(button);
        // 鼠标悬停事件监听器
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startColorTransition(button, new Color(49, 36, 1));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startColorTransition(button, new Color(139, 69, 19));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                button.setBackground(Color.GREEN); // 鼠标点击时颜色变为绿色
            }
        });
        button.setBackground(new Color(139, 69, 19));
        button.setForeground(Color.white);
        add(button);
    }
    private static void startColorTransition(JButton button, Color targetColor) {
        if (timer != null && timer.isRunning()) {
            return; // 如果渐变动画已经在进行中，则直接返回
        }

        Color initialColor = button.getBackground();
        currentStep = 0; // 重置当前步数

        timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentStep >= NUM_STEPS) {
                    timer.stop(); // 达到渐变的总步数后停止定时器
                } else {
                    float ratio = (float) currentStep / NUM_STEPS; // 计算当前步数与总步数的比例
                    Color transitionColor = getTransitionColor(initialColor, targetColor, ratio); // 计算过渡颜色
                    button.setBackground(transitionColor);
                    currentStep++;
                }
            }
        });

        timer.start();
    }

    private static Color getTransitionColor(Color initialColor, Color targetColor, float ratio) {
        int red = (int) (initialColor.getRed() + (targetColor.getRed() - initialColor.getRed()) * ratio);
        int green = (int) (initialColor.getGreen() + (targetColor.getGreen() - initialColor.getGreen()) * ratio);
        int blue = (int) (initialColor.getBlue() + (targetColor.getBlue() - initialColor.getBlue()) * ratio);
        return new Color(red, green, blue);
    }
}
