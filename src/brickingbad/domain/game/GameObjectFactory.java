package brickingbad.domain.game;

public class GameObjectFactory {

    private static GameObjectFactory instance;

    public static GameObjectFactory getInstance() {
        if (instance == null) {
            instance = new GameObjectFactory();
        }
        return instance;
    }

    private GameObjectFactory() {

    }
}
