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

        MusicThread musicThread = new MusicThread("resource/Music/09 高级动物.wav", true);

        musicThread.start();
    }
}
