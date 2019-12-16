package brickingbad.controller;

import brickingbad.ui.effects.MineBrickExplodeEffect;
import brickingbad.ui.game.RunningModePanel;

import brickingbad.services.sound.AudioPlayer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class EffectsController {

  private static EffectsController instance;
  private Timer timer;
  private Clip clip;


  private EffectsController() {

  }

  public static EffectsController getInstance() {
    if (instance == null) {
      instance = new EffectsController();
    }
    return instance;
  }

  public void showMineBrickExplodeEffect(double x, double y) {
    MineBrickExplodeEffect effect = new MineBrickExplodeEffect(x, y);
    RunningModePanel.getInstance().addEffect(effect);
  }


  public void startHeartBeat(){
    timer = new Timer(9000, e -> {
      clip = playAudio("lastLife");
    });

    timer.setInitialDelay(0);
    timer.setRepeats(true);
    timer.start();
  }

  public void stopHeartBeat(){
    if (timer != null){
      clip.stop();
      clip.close();
      timer.setRepeats(false);
      timer.stop();
    }
  }


  public Clip playAudio(String name)  {

    try {
      AudioPlayer sound = new AudioPlayer("resources/sounds/"+name+".wav");
      return sound.getClip();
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      e.printStackTrace();
    }

    return null;
  }

  public void load(){

    initiateAudio("resources/sounds/brickBroke.wav");
    initiateAudio("resources/sounds/start.wav");
    initiateAudio("resources/sounds/lifeLost.wav");
    initiateAudio("resources/sounds/closeGame.wav");
    initiateAudio("resources/sounds/button.wav");
    initiateAudio("resources/sounds/mineBrick.wav");
    initiateAudio("resources/sounds/halfMetalBrick.wav");
    initiateAudio("resources/sounds/lastLife.wav");
    initiateAudio("resources/sounds/endLaugh.wav");
    initiateAudio("resources/sounds/congratz.wav");
    initiateAudio("resources/sounds/laserGun.wav");

  }

  public void initiateAudio(String path){

    Clip clip = null;
    AudioInputStream audio;
    try {

      audio =
              AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
      clip = AudioSystem.getClip();
      clip.open(audio);



    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      e.printStackTrace();
    }
  }
}
