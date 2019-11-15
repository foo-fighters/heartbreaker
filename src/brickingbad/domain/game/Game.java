package brickingbad.domain.game;

import brickingbad.domain.game.powerup.*;
import brickingbad.domain.game.border.*;
import brickingbad.domain.game.alien.*;
import brickingbad.domain.game.brick.*;
import brickingbad.domain.game.*;

import java.util.ArrayList;
import java.util.Date;

public class Game {

    private Game instance;

    private Paddle paddle;
    private ArrayList<Ball> balls;
    private Ground ground;
    private Wall wall;
    private ArrayList<Brick> bricks;
    private ArrayList<PowerUp> activePowerUps;
    private ArrayList<PowerUp> storedPowerUps;
    private int score;
    private int lives;
    private Date time;

    private ArrayList<GameObjectListener> objectListeners;

}
