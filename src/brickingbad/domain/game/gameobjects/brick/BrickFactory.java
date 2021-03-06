package brickingbad.domain.game.gameobjects.brick;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * BrickingFactory is responsible for creating the desired brick type.
 */
public class BrickFactory {
    /* OVERVIEW: creates different kinds of bricks (simple brick, halfMetal brick,
                                                        mine brick, wrapper brick).
    */
    /**
     * Maintains the single instance of {@link BrickFactory} since it is implemented as a singleton.
     */
    private static BrickFactory instance;

    /**
     * Straightforward implementation of a singleton method to obtain the instance.
     *
     * @return the single instance of the class
     */
    public static BrickFactory getInstance() {
        if (instance == null) {
            instance = new BrickFactory();
        }
        return instance;
    }

    private BrickFactory() {

    }

    /**
     * creates the simple brick type.
     *
     * @param n simple brick count that needs to be created.
     * @return simpleBricks as an ArrayList.
     */
    public ArrayList<Brick> createSimpleBricks(int n) {
        // REQUIRES: an integer greater than 0
        // MODIFIES:
        // EFFECTS:  returns an array of newly created bricks
        ArrayList<Brick> simpleBricks = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            simpleBricks.add(new SimpleBrick());
        }
        return simpleBricks;
    }

    /**
     * creates the half metal brick type.
     *
     * @param n half metal brick count that needs to be created.
     * @return halfMetalBrick as an ArrayList.
     */
    public ArrayList<Brick> createHalfMetalBricks(int n) {
        // REQUIRES: an integer greater than 0
        // MODIFIES:
        // EFFECTS:  returns an array of newly created bricks
        ArrayList<Brick> halfMetalBricks = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            halfMetalBricks.add(new HalfMetalBrick());
        }
        return halfMetalBricks;
    }

    /**
     * creates the mine brick type.
     *
     * @param n mine brick count that needs to be created.
     * @return mineBricks as an ArrayList.
     */
    public ArrayList<Brick> createMineBricks(int n) {
        // REQUIRES: an integer greater than 0
        // MODIFIES:
        // EFFECTS:  returns an array of newly created bricks
        ArrayList<Brick> mineBricks = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            mineBricks.add(new MineBrick());
        }
        return mineBricks;
    }

    /**
     * creates the wrapper brick type.
     *
     * @param n wrapper brick count that needs to be created.
     * @return wrapperBricks as an ArrayList.
     */
    public ArrayList<Brick> createWrapperBricks(int n) {
        // REQUIRES: integer greater than 0
        // MODIFIES:
        // EFFECTS:  returns an array of newly created bricks
        ArrayList<Brick> wrapperBricks = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            wrapperBricks.add(new WrapperBrick());
        }
        return wrapperBricks;
    }

    /**
     * creates brick for a required brick type
     *
     * @param className gives the required brick type
     * @return brick
     */
    public Brick createBrick(String className) throws InvocationTargetException, NoSuchMethodException,
            ClassNotFoundException, InstantiationException, IllegalAccessException {
        // REQUIRES: a non-null string representing a class name
        // MODIFIES:
        // EFFECTS:  returns a brick
        Brick brick = null;
        try {
            brick = (Brick) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException |
                NoSuchMethodException |
                InstantiationException |
                IllegalAccessException |
                InvocationTargetException e) {
            throw e;
        }
        return brick;
    }
}
