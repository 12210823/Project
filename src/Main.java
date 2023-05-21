import view.MainGameFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGameFrame mainFrame = new MainGameFrame(800, 500);
            mainFrame.setVisible(true);
        });
    }
}
