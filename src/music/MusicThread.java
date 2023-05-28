package music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;

public class MusicThread extends Thread {
    private final String musicFilePath;
    private final boolean isLoop;

    public MusicThread(String filePath,boolean isLoop) {
        this.musicFilePath = filePath;
        this.isLoop = isLoop;
    }
    public MusicThread(URL filePath,boolean isLoop) {
        this.musicFilePath = filePath.getPath();
        this.isLoop = isLoop;
    }

    @Override
    public void run() {
        try {
            File music = new File(musicFilePath);
            URL url = music.toURI().toURL();
            // 加载音乐文件
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);

            // 创建音频剪辑
            Clip clip = AudioSystem.getClip();

            // 打开音频流
            clip.open(audioInputStream);

            if(isLoop){
                // 设置循环播放
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            // 播放音频
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
