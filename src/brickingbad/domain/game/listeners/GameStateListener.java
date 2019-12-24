package brickingbad.domain.game.listeners;

public interface GameStateListener {
    void winGame();
    void loseGame();
    void setAlreadyWon(boolean b);
}
