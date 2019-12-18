package brickingbad.audio;

import brickingbad.domain.game.listeners.SfxListener;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AudioController implements SfxListener {

    private static AudioController instance;
    private Clip menuClip;
    private Clip transitionClip;
    private Clip mainLoopClip;
    private LineListener transitionListener;

    private AudioController() {
        try {
            menuClip = AudioSystem.getClip();
            transitionClip = AudioSystem.getClip();
            mainLoopClip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        transitionListener = event -> {
            if(event.getType() == LineEvent.Type.STOP) playMainLoopMusic();
        };
    }

    public static AudioController getInstance() {
        if (instance == null) {
            instance = new AudioController();
        }
        return instance;
    }

    @Override
    public void playClip(String clipName) {

    }

    public void playMenuMusic() {
        transitionClip.removeLineListener(transitionListener);
        transitionClip.stop();
        transitionClip.close();
        mainLoopClip.stop();
        mainLoopClip.close();
        File music = new File("resources/sounds/menu_loop.wav");
        try {
            menuClip.open(AudioSystem.getAudioInputStream(music));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        menuClip.start();
        menuClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playTransitionMusic() {
        File music = new File("resources/sounds/game_transition.wav");
        long pos = (menuClip.getLongFramePosition() + menuClip.getFrameLength() / 1500) % menuClip.getFrameLength();
        System.out.println(pos);
        try {
            transitionClip.open(AudioSystem.getAudioInputStream(music));
            transitionClip.setFramePosition((int)pos);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        transitionClip.start();
        try {
            TimeUnit.MICROSECONDS.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        menuClip.stop();
        menuClip.close();
        transitionClip.addLineListener(transitionListener);
    }

    public void playMainLoopMusic() {
        File music = new File("resources/sounds/game_music.wav");
        try {
            mainLoopClip.open(AudioSystem.getAudioInputStream(music));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        mainLoopClip.start();
        mainLoopClip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
