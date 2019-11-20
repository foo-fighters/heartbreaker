package brickingbad.domain.game.brick;

public class BrickFactory {

    private static BrickFactory instance;

    public static BrickFactory getInstance() {

        if (instance == null) {
            instance = new BrickFactory();
        }
        return instance;

    }

    private BrickFactory() {

    }

    public


}
