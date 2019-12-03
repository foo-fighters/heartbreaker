package brickingbad.domain.game.brick;

import java.lang.reflect.InvocationTargetException;
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
        ArrayList<Brick> halfMetalBricks = new ArrayList<>();
        for (int i = 0; i<n; i++) {
            halfMetalBricks.add(new HalfMetalBrick());
        }
        return halfMetalBricks;
    }

    public ArrayList<Brick> createMineBricks(int n) {
        ArrayList<Brick> mineBricks = new ArrayList<>();
        for (int i = 0; i<n; i++) {
            mineBricks.add(new MineBrick());
        }
        return mineBricks;
    }

    public ArrayList<Brick> createWrapperBricks(int n) {
        ArrayList<Brick> wrapperBricks = new ArrayList<>();
        for (int i = 0; i<n; i++) {
            wrapperBricks.add(new WrapperBrick());
        }
        return wrapperBricks;
    }

    public Brick createBrick(String className) {
        Brick brick;
        try {
            brick = (Brick) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException |
                NoSuchMethodException |
                InstantiationException |
                IllegalAccessException |
                InvocationTargetException e){
            return new SimpleBrick();
        }
        return brick;
    }


}
