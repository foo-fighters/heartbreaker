package brickingbad.ui.game;

import brickingbad.controller.GameController;
import brickingbad.domain.game.*;
import brickingbad.domain.game.brick.HalfMetalBrick;
import brickingbad.domain.physics.PhysicsEngine;
import brickingbad.ui.components.UIGameObject;
import brickingbad.ui.components.containers.GameButtonPanel;
import brickingbad.ui.game.animation.Animation;
import brickingbad.ui.game.animation.Animator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class RunningModePanel extends JPanel implements GameObjectListener, AnimationListener {

  private static RunningModePanel instance;

  // a CopyOnArrayList generates a new copy of the ArrayList when there is a write operation, making it thread-safe
  // this is more costly but since the number of traversals heavily outnumber the number or writes in our case,
  //    and we are working with multiple threads, it is a logical choice.
  private CopyOnWriteArrayList<UIGameObject> uiObjects;

  private GameButtonPanel gameButtonPanel;
  private JLabel scoreLabel;
  private BufferedImage background;

  private BufferedImage heart;
  private BufferedImage heart_empty;

  private ArrayList<Animation> currentAnimations;

  private RunningModePanel() {
    Animator.getInstance(this).start();
    PhysicsEngine.getInstance().start();
    setLayout(null);
    uiObjects = new CopyOnWriteArrayList<>();
    initUI();
    loadBackgroundImage("resources/sprites/background.png");
    loadHeartImage("resources/sprites/heart.png");
    loadHeartEmptyImage("resources/sprites/heart_empty.png");
    GameController.getInstance().addObjectListener(this);
    GameController.getInstance().addAnimationListener(this);
    currentAnimations = new ArrayList<>();
    this.addKeyListener(new GameKeyboardListener());

    scoreLabel = new JLabel();
    scoreLabel.setText("0");
    scoreLabel.setForeground(Color.white);
  }

  public static RunningModePanel getInstance() {
    if (instance == null) {
      instance = new RunningModePanel();
    }
    return instance;
  }

  private void initUI() {
    gameButtonPanel = new GameButtonPanel();
    JPanel container = new JPanel(new BorderLayout());
    container.setSize(GameConstants.screenWidth, (int)GameConstants.menuAreaHeight);
    container.setOpaque(true);
    container.setBackground(Color.darkGray);
    add(container);
    container.add(gameButtonPanel, BorderLayout.LINE_START);
  }

  @Override
  public void addObject(GameObject object) {
    uiObjects.add(new UIGameObject(object, this));
  }

  @Override
  public void removeObject(GameObject object) {
    uiObjects.removeIf(uiGameObject -> uiGameObject.containsObject(object));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
    ArrayList<Animation> animationsCopy = new ArrayList<>(currentAnimations);
    for(Animation animation : animationsCopy) {
      animation.drawFrame(g);
    }
    for (Iterator<UIGameObject> iterator = uiObjects.iterator(); iterator.hasNext(); ) {
      UIGameObject object = iterator.next();
      object.paintComponent(g);
    }
    drawHearts(g, Game.getInstance().getLives());
  }


  private void loadBackgroundImage(String path) {
    try {
      this.background = ImageIO.read(new File(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadHeartImage(String path) {
    try {
      this.heart = ImageIO.read(new File(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadHeartEmptyImage(String path) {
    try {
      this.heart_empty = ImageIO.read(new File(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void reset(){

  }

  public void invokeGodMode() {
    loadBackgroundImage("resources/sprites/godmode.png");
  }

  public void drawHearts(Graphics g, int livesLeft){
    int iteration = 0;

    for (int i = 0; i < livesLeft; i++){
      g.drawImage(heart,getWidth()-150 + iteration,getHeight()-50, GameConstants.heartSize,GameConstants.heartSize,null);
      iteration += GameConstants.heartSize + 10;
    }
    for (int i = 0; i < (3-livesLeft); i++){
      g.drawImage(heart_empty,getWidth()-150 + iteration,getHeight()-50, GameConstants.heartSize,GameConstants.heartSize,null);
      iteration += GameConstants.heartSize + 10;
    }
  }

  public void resetUI() {
    uiObjects = new CopyOnWriteArrayList<>();
  }

  public void setScore(int score) {
    gameButtonPanel.setUIScore(score);
  }

  @Override
  public void addAnimation(String animationName, Object... args)
          throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
    Class animationClass = Class.forName("brickingbad.ui.game.animation." + animationName);
    Constructor constructor = animationClass.getConstructors()[0];
    Object[] params = new Object[args.length + 1];
    params[0] = this;
    for(int i = 0; i < args.length; i++) {
      params[i + 1] = args[i];
    }
    currentAnimations.add((Animation) constructor.newInstance(params));
  }

  @Override
  public void removeAnimation(String animationName) {
    ArrayList<Animation> animCopy = new ArrayList<>(currentAnimations);
    for(Animation anim: animCopy) {
      if(anim.getClass().getSimpleName().equals(animationName)) {
        currentAnimations.remove(anim);
      }
    }
  }

  @Override
  public void stopAnimation(Animation animation) {
    currentAnimations.remove(animation);
  }

  @Override
  public void updateBalls(String stateModifier) {
    for(UIGameObject object: uiObjects) {
      if(object.getGameObject() instanceof Ball) {
        object.setSprite(String.format("ball%s", stateModifier));
      }
    }
  }

  @Override
  public void crackHalfMetalBrick(HalfMetalBrick brick) {
    for(UIGameObject object: uiObjects) {
      if(object.getGameObject() == brick) {
        object.setSprite(String.format("halfmetalbrick_cracked"));
        return;
      }
    }
  }
}
