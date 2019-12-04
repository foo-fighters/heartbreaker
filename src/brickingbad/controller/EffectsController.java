package brickingbad.controller;

import brickingbad.ui.effects.MineBrickExplodeEffect;
import brickingbad.ui.game.RunningModePanel;

import brickingbad.services.sound.AudioPlayer;
import brickingbad.ui.effects.MineBrickExplodeEffect;
import brickingbad.ui.game.RunningModePanel;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class EffectsController {

  private static EffectsController instance;



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

  public void playAudio(String name)  {

    try {
      AudioPlayer sound = new AudioPlayer("resources/sounds/"+name+".wav");
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      e.printStackTrace();
    }
  }


  public void load(){

    initiateAudio("resources/sounds/brickBroke.wav");
    initiateAudio("resources/sounds/start.wav");
    initiateAudio("resources/sounds/lifeLost.wav");
    initiateAudio("resources/sounds/closeGame.wav");

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
