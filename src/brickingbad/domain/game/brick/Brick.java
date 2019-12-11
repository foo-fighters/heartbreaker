package brickingbad.domain.game.brick;

import brickingbad.controller.EffectsController;
import brickingbad.domain.game.Game;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.physics.Vector;
import brickingbad.services.sound.AudioPlayer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public abstract class Brick extends GameObject {
    @Override
    public void destroy(boolean destroyedByBall) {
        if (destroyedByBall) {
            EffectsController.getInstance().playAudio("brickBroke");
        }

        Game.getInstance().anyBricksLeft();
        Game.getInstance().brickDestroyed();
        super.destroy();
    }

    @Override
    public void collide(GameObject object) {
        setVelocity(new Vector(-getVelocity().getX(), getVelocity().getY()));
    }

    public void startMovement() {
    }

}
