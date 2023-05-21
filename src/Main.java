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
        File musicFile = new File("resource/Music/09 高级动物.wav");

        URL musicURL = null;
        try {
            musicURL = musicFile.toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }


        // 创建音乐线程实例
        MusicThread musicThread = new MusicThread(musicURL, true);

        // 创建线程并启动
        Thread music = new Thread(musicThread);
        music.start();

        // 调整音量
        musicThread.setVolume(0.5f); // 设置音量为一半
        float volume = musicThread.getVolume(); // 获取当前音量
        System.out.println("volume: " + volume);
    }
}
