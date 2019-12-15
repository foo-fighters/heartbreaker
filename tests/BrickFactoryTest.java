import brickingbad.domain.game.brick.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;


public class BrickFactoryTest {

    private BrickFactory brickFactory;

    @Before
    public void setUp() {
        brickFactory = brickingbad.domain.game.brick.BrickFactory.getInstance();
    }

    @Test
    public void getInstanceReturnsInstance() {
        assertNotNull(brickFactory);
    }

    @Test
    public void isBrickReturnedByTypeTest() throws ClassNotFoundException, NoSuchMethodException,
            InstantiationException, IllegalAccessException, InvocationTargetException {
        Brick simpleBrick = brickFactory.createBrick("brickingbad.domain.game.brick.SimpleBrick");
        Brick mineBrick =  brickFactory.createBrick("brickingbad.domain.game.brick.MineBrick");
        Brick wrapperBrick = brickFactory.createBrick("brickingbad.domain.game.brick.WrapperBrick");
        Brick halfMetalBrick = brickFactory.createBrick("brickingbad.domain.game.brick.HalfMetalBrick");
        assertTrue(simpleBrick instanceof SimpleBrick);
        assertTrue(halfMetalBrick instanceof HalfMetalBrick);
        assertTrue(mineBrick instanceof MineBrick);
        assertTrue(wrapperBrick instanceof WrapperBrick);
    }

    @Test(expected = ClassNotFoundException.class)
    public void createBrickErrorTest() throws ClassNotFoundException, NoSuchMethodException,
            InstantiationException, IllegalAccessException, InvocationTargetException {
        Brick errorBrick = brickFactory.createBrick("errorString");
    }

    @Test
    public void isBrickListReturnedByTypeTest() {
        int expectedSize = 10;
        ArrayList<Brick> simpleBrickList = new ArrayList<>(brickFactory.createSimpleBricks(expectedSize));
        ArrayList<Brick> mineBrickList = new ArrayList<>(brickFactory.createMineBricks(expectedSize));
        ArrayList<Brick> halfMetalBrickList = new ArrayList<>(brickFactory.createHalfMetalBricks(expectedSize));
        ArrayList<Brick> wrapperBrickList = new ArrayList<>(brickFactory.createWrapperBricks(expectedSize));

        assertEquals(expectedSize, simpleBrickList.size());
        assertEquals(expectedSize, mineBrickList.size());
        assertEquals(expectedSize, halfMetalBrickList.size());
        assertEquals(expectedSize, wrapperBrickList.size());

        for(Brick brick: simpleBrickList) {
            assertTrue(brick instanceof SimpleBrick);
        }
        for(Brick brick: mineBrickList) {
            assertTrue(brick instanceof MineBrick);
        }
        for(Brick brick: halfMetalBrickList) {
            assertTrue(brick instanceof HalfMetalBrick);
        }
        for(Brick brick: wrapperBrickList) {
            assertTrue(brick instanceof WrapperBrick);
        }
    }
}



