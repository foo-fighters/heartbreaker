package brickingbad.domain.game.alien;

import brickingbad.domain.game.Game;

public class AlienStrategyFactory {
    private int brickCount = Game.getInstance().getBrickCount();
    private int startCount = Game.getInstance().startBrickCount;
    public static AlienStrategyFactory instance;

    public static AlienStrategyFactory getInstance() {
        if (instance == null) {
            instance = new AlienStrategyFactory();
        }
        return instance;
    }

    public AlienStrategy getStrategy() {
        if (startCount * 0.7 <= brickCount) {
            return new CooperativeAlien();
        } else if (startCount * 0.3 >= brickCount) {
            return new CombinedTwoAliens();
        } else if (startCount * 0.5 >= brickCount && startCount * 0.4 <= brickCount) {
            return new ProtectingAlien();
        } else if (startCount * 0.6 >= brickCount && startCount * 0.5 <= brickCount) {
            return new RepairingAlien();
        } else if
            ((startCount * 0.4 >= brickCount && startCount * 0.3 <= brickCount) || (startCount * 0.7 >= brickCount && startCount * 0.6 <= brickCount))
        {
            return new DrunkAlien();
        }
        else{
            return new DefaultAlienStrategy();
        }
    }
}