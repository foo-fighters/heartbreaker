import brickingbad.domain.game.brick.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;


public class BrickFactoryTest {

    private BrickFactory brickFactory;
    private Brick simpleBrick;
    private Brick halfMetalBrick;
    private Brick mineBrick;
    private Brick wrapperBrick;

    @Before
    public void setUp() {
        brickFactory = brickingbad.domain.game.brick.BrickFactory.getInstance();
        simpleBrick = brickFactory.createBrick("brickingbad.domain.game.brick.SimpleBrick");
        mineBrick =  brickFactory.createBrick("brickingbad.domain.game.brick.MineBrick");
        wrapperBrick =  brickFactory.createBrick("brickingbad.domain.game.brick.WrapperBrick");
        halfMetalBrick =brickFactory.createBrick("brickingbad.domain.game.brick.HalfMetalBrick");
    }

    @Test
    public void getInstanceReturnsInstance() {
        assertNotNull(brickFactory);
    }

    @Test
    public void isABrickReturnedByType() throws Exception {

        /*boolean simpleBrickCheck = (simpleBrick instanceof SimpleBrick);
        boolean mineBrickCheck = (mineBrick instanceof MineBrick);
        boolean wrapperBrickCheck = (wrapperBrick instanceof WrapperBrick);
        boolean halfMetalBrickCheck = (halfMetalBrick instanceof HalfMetalBrick);*/

            assertTrue(simpleBrick instanceof SimpleBrick);
            assertTrue(mineBrick instanceof MineBrick);
            assertTrue(wrapperBrick instanceof WrapperBrick);
            assertTrue(halfMetalBrick instanceof HalfMetalBrick);

    }

    @Test
    public void isBrickListReturnedByTypeTest() {
        int n = 100;
        ArrayList simpleBrickList = new ArrayList<>(brickFactory.createSimpleBricks(n));
        ArrayList mineBrickList = new ArrayList<>(brickFactory.createMineBricks(n));
        ArrayList halfMetalBrickList = new ArrayList<>(brickFactory.createHalfMetalBricks(n));
        ArrayList wrapperBrickList = new ArrayList<>(brickFactory.createWrapperBricks(n));

        isBricklistReturnedByType(SimpleBrick.class, simpleBrickList, n);
        isBricklistReturnedByType(MineBrick.class, mineBrickList , n);
        isBricklistReturnedByType(WrapperBrick.class, wrapperBrickList, n);
        isBricklistReturnedByType(HalfMetalBrick.class, halfMetalBrickList, n);
    }


    public void isBricklistReturnedByType(Class Brick, ArrayList brickList, int n) {
        boolean checkBrick = false;
        //ArrayList testList = new ArrayList<>(CreateBrickListTest(n, brickClass));
        if(n != brickList.size()){
            assertTrue("Size does not match with the test size", false);
            return;
        }

        for (int i = 0; i < brickList.size(); i++) {
            if(brickList.get(i) instanceof Brick){
                checkBrick = true;
            }else{
                assertTrue("Object is not an instance of" + Brick.getName()+ ".", checkBrick);
            }
            checkBrick = false;
        }
    }
}



