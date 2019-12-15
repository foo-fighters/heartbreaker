import brickingbad.domain.physics.Vector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VectorTest {

  private Vector v1;
  private Vector v2;

  @Before
  public void setUp() {
    v1 = new Vector(3, 2);
    v2 = new Vector(4, 6);
  }

  @Test
  public void addVectorAddsTwoVectors() {
    Vector expected = new Vector(7.0, 8.0);
    Vector given = v1.sum(v2);
    assertEquals(expected, given);
  }

  @Test
  public void productWorksOnPositiveInputs() {
    Vector expected = new Vector(6.0, 4.0);
    Vector given = v1.product(2);
    assertEquals(expected, given);
  }

  @Test
  public void productWorksOnNegativeInputs() {
    Vector expected = new Vector(-6.0, -4.0);
    Vector given = v1.product(-2);
    assertEquals(expected, given);
  }

  @Test
  public void productWorksOnZero() {
    Vector expected = new Vector(0.0, 0.0);
    Vector given = v1.product(0);
    assertEquals(expected, given);
  }

  @Test
  public void vectorRepOK() {
    assertTrue(v1.repOK());
    assertTrue(v2.repOK());
  }

}
