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
        simpleBrick = brickFactory.createBrick("SimpleBrick");
        mineBrick =  brickFactory.createBrick("MineBrick");
        wrapperBrick =  brickFactory.createBrick("WrapperBrick");
        halfMetalBrick =brickFactory.createBrick("HalfMetalBrick");
    }

    @Test
    public void getInstanceReturnsInstance() {
        assertNotNull(brickFactory);
    }

    @Test
    public void isABrickReturnedByType() throws Exception {

        if (simpleBrick instanceof SimpleBrick) {
            assertTrue("Brick is not SimpleBrick!", true);
        }
        if (halfMetalBrick instanceof HalfMetalBrick) {
            assertTrue("Brick is not HalfMetalBrick!", true);
        }
        if (mineBrick instanceof MineBrick) {
            assertTrue("Brick is not MineBrick!", true);
        }
        if (wrapperBrick instanceof WrapperBrick) {
            assertTrue("Brick is not WrapperBrick!", true);
        }
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



