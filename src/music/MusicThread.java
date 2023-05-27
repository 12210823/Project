package music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class MusicThread implements Runnable {
    private final URL musicPath;
    private final boolean isLoop;
    private FloatControl gainControl;

    public MusicThread(URL musicPath, boolean isLoop) {
        this.musicPath = musicPath;
        this.isLoop = isLoop;
    }

    @Override
    public void run() {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(musicPath);
            clip.open(ais);

            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            if (isLoop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setVolume(float volume) {
        if (gainControl != null) {
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }

    public float getVolume() {
        if (gainControl != null) {
            return (float) Math.pow(10.0, gainControl.getValue() / 20.0);
        }
        return 0.0f;
    }
}
