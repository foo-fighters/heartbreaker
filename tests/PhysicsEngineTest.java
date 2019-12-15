import brickingbad.domain.game.Ball;
import brickingbad.domain.game.GameConstants;
import brickingbad.domain.game.GameObject;
import brickingbad.domain.game.brick.SimpleBrick;
import brickingbad.domain.physics.PhysicsEngine;
import brickingbad.domain.physics.Vector;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PhysicsEngineTest {

  private PhysicsEngine engine;
  private GameObject brick1;
  private GameObject brick2;
  private GameObject ball1;
  private GameObject ball2;
  private ArrayList<GameObject> testObjects;

  @Before
  public void setUp() {
    engine = PhysicsEngine.getInstance();
    brick1 = new SimpleBrick();
    brick2 = new SimpleBrick();
    brick1.setDynamic(true);
    brick2.setDynamic(true);
    ball1 = new Ball(new Vector());
    ball2 = new Ball(new Vector());
    testObjects = new ArrayList<>();
    testObjects.add(brick1);
    testObjects.add(brick2);
    testObjects.add(ball1);
    testObjects.add(ball2);
  }

  @Test
  public void testInstanceReturn() {
    assertNotNull(engine);
  }

  @Test
  public void testUpdatePositions() {
    Vector v1 = new Vector(5, 0);
    Vector v2 = new Vector();
    Vector v3 = new Vector(3, -3);
    Vector v4 = new Vector(-1, -1);
    Vector expected1 = v1.product(1.0 / GameConstants.calculationsPerSecond);
    Vector expected2 = v2.product(1.0 / GameConstants.calculationsPerSecond);
    Vector expected3 = v3.product(1.0 / GameConstants.calculationsPerSecond);
    Vector expected4 = v4.product(1.0 / GameConstants.calculationsPerSecond);
    brick1.setVelocity(v1);
    brick2.setVelocity(v2);
    ball1.setVelocity(v3);
    ball2.setVelocity(v4);
    engine.updatePositions(testObjects);
    assertEquals(expected1, brick1.getPosition());
    assertEquals(expected2, brick2.getPosition());
    assertEquals(expected3, ball1.getPosition());
    assertEquals(expected4, ball2.getPosition());
  }

  @Test
  public void testAreCollidingRectRect() {
    brick1.setSize(new Vector(5, 3));
    brick2.setSize(new Vector(2, 4));
    brick1.setPosition(new Vector(1, 1));
    brick2.setPosition(new Vector(4, 4));
    assertTrue(engine.areColliding(brick1, brick2));
    brick2.setPosition(new Vector(8, 8));
    assertFalse(engine.areColliding(brick1, brick2));
  }

  @Test
  public void testAreCollidingCircleCircle() {
    ball1.setSize(new Vector(6, 6));
    ball2.setSize(new Vector(4, 4));
    ball1.setPosition(new Vector(0, -2));
    ball2.setPosition(new Vector(0, 2));
    assertTrue(engine.areColliding(ball1, ball2));
    ball2.setPosition(new Vector(0, 5));
    assertFalse(engine.areColliding(ball1, ball2));
  }

  @Test
  public void testMixedColliding() {
    brick1.setSize(new Vector(6, 6));
    ball1.setSize(new Vector(2, 2));
    brick1.setPosition(new Vector(0, 0));
    ball1.setPosition(new Vector(0, -3.5));
    assertTrue(engine.mixedColliding(ball1, brick1));
    ball1.setPosition(new Vector(0, -5));
    assertFalse(engine.mixedColliding(ball1, brick1));
    ball1.setPosition(new Vector(3.5, 3.5));
    assertTrue(engine.mixedColliding(ball1, brick1));
    ball1.setPosition(new Vector(4.5, 4.5));
    assertFalse(engine.mixedColliding(ball1, brick1));
  }

}
