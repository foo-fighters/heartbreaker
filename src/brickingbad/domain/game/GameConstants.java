package brickingbad.domain.game;

import java.util.Map;

public class GameConstants {

    // Screen size
    public final static int screenHeight = 720;
    public final static int screenWidth = 1280;
    public final static double menuAreaHeight = screenHeight * 0.05;
    public final static double brickAreaHeight = screenHeight * 0.5;
    public final static double alienAreaHeight = screenHeight * 0.2;
    public final static double paddleAreaHeight = screenHeight * 0.25;

    // Object sizes
    public final static int ballSize = 17;
    public final static int paddleLength = screenWidth / 10;
    public final static int paddleThickness = 20;
    public final static int rectangularBrickLength = paddleLength / 5;
    public final static int rectangularBrickThickness = 20;
    public final static int circularBrickSize = 20;
    public final static int alienSize = 50;
    public final static int powerupSize = 20;

    // Ball movement
    public final static double ballLaunchSpeed = screenHeight * 0.4;

    // Paddle movement
    public final static double regularPaddleMovementSpeed = paddleLength * 2.0;
    public final static double slowPaddleMovementSpeed = paddleLength;
    public final static double paddleStopDistance = paddleLength / 2.0;
    public final static double regularPaddleRotationSpeed = 20.0;
    public final static double fastPaddleRotationSpeed = 45.0;
    public final static double paddleRotationLimit = 45.0;

    // Brick movement
    public final static int brickMovementSpeed = paddleLength / 4;
    public final static double brickMovementProbability = 0.1;
    public final static double mineBrickMovementRadius = paddleLength * 1.5;

    // Building mode
    public final static int simpleBrickLimit = 75; //change to 75 in phase 2
    public final static int halfMetalBrickLimit = 10; //change to 10 in phase 2
    public final static int mineBrickLimit = 5; //change to 5 in phase 2
    public final static int wrapperBrickLimit = 0; //change to 10 in phase 2

    // Power-ups
    public final static int powerupFallSpeed = paddleLength * 4;
    public final static int gangOfBallsMultiplier = 10;
    public final static int laserGunCharges = 5;
    public final static int chemicalBallDuration = 60;
    public final static int tallerPaddleDuration = 30;
    public final static int magnetDuration = 30;
    public final static int tallerPaddleMultiplier = 2;

    // Extras
    public final static double mineBrickExplosionRadius = paddleLength; // * 2 later
    public final static double fireBallDestructionRadius = paddleLength * 0.5;
    public final static int paddleHeightOnScreen = screenHeight / 10;
    public static int framesPerSecond = 60;
    public static int calculationsPerSecond = 100;
}
