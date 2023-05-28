import music.MusicThread;
import view.MainGameFrame;

import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGameFrame mainFrame = new MainGameFrame(800, 500);
            mainFrame.setVisible(true);
        });

        URL musicURL = Main.class.getClassLoader().getResource("/Music/bgMusic.wav");
        MusicThread musicThread = new MusicThread("resource/Music/bgMusic.wav", true);

        musicThread.start();
    }
}
