package brickingbad.services.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    Long currentFrame;

    String status;
    AudioInputStream audioInputStream;
    static String filePath;

    public AudioPlayer(String filePath)
            throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {

        long f1 = System.currentTimeMillis();
        // create AudioInputStream object
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        long f2 = System.currentTimeMillis();
        // create clip reference
        Clip clip = AudioSystem.getClip();
        long f3 = System.currentTimeMillis();
        // open audioInputStream to the clip
        clip.open(audioInputStream);
        long f4 = System.currentTimeMillis();
        clip.start();
        long f5 = System.currentTimeMillis();

        System.out.println("first = " + (f2-f1));
        System.out.println("second = " + (f3-f2));
        System.out.println("third = " + (f4-f3));
        System.out.println("fourth = " + (f5-f4));
    }

}
