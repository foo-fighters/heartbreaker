package brickingbad.domain.game.brick;

import java.util.ArrayList;

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

    public ArrayList<Brick> createSimpleBricks(int n) {

        ArrayList<Brick> simpleBricks = new ArrayList<>();

        for (int i = 0; i<n; i++){
            simpleBricks.add(new SimpleBrick());
        }

        return simpleBricks;
    }

    public ArrayList<Brick> createHalfMetalBricks(int n) {
        return null;
    }

    public ArrayList<Brick> createMineBricks(int n) {
        return null;
    }

    public ArrayList<Brick> createWrapperBricks(int n) {
        return null;
    }


}
