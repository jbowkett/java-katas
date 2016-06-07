package info.bowkett.katas;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.NoSuchElementException;

/**
 * Created by jbowkett on 06/06/2016.
 */
public class BinaryChopTest {

  @Test
  public void ensureOneElementInArrayIsFound(){
    final int toFind = 35;
    final Integer [] elements = {toFind};

    final BinaryChop<Integer> chop = new BinaryChop<>();
    final int result = chop.indexOf(elements, toFind);
    assertEquals(0, result);
  }

  @Test
  public void ensureFindsElementWhenItIsHigherInArrayOfFourElements(){
    Integer toFind = 4;
    final Integer [] elements = {1, 2, toFind, 6};

    final BinaryChop<Integer> chop = new BinaryChop<>();
    final int result = chop.indexOf(elements, toFind);
    assertEquals(2, result);
  }

  @Test
  public void ensureFindsElementWhenItIsHigherInArrayOfFiveElements(){
    Integer five = 5;
    final Integer [] elements = {1, 2, 3, 4, five};

    final BinaryChop<Integer> chop = new BinaryChop<>();
    final int result = chop.indexOf(elements, five);
    assertEquals(4, result);
  }

  @Test
  public void ensureFindsElementWhenItIsLowerInArrayOfFourElements(){
    Integer two = 2;
    final Integer [] elements = {1, two, 3, 6};

    final BinaryChop<Integer> chop = new BinaryChop<>();
    final int result = chop.indexOf(elements, two);
    assertEquals(1, result);
  }

  @Test
  public void ensureFindsElementWhenItIsLowerInArrayOfFiveElements(){
    Integer one = 1;
    final Integer [] elements = {one, 2, 3, 4, 5};

    final BinaryChop<Integer> chop = new BinaryChop<>();
    final int result = chop.indexOf(elements, one);
    assertEquals(0, result);
  }

  @Test(expected = NoSuchElementException.class)
  public void ensureNonPresentValueThrowsExceptionOnOddNumberOfElements(){
    final Integer [] elements = {1, 2, 3, 4, 5};

    final BinaryChop<Integer> chop = new BinaryChop<>();
    chop.indexOf(elements, 42);
    fail("Exception expected");
  }

  @Test(expected = NoSuchElementException.class)
  public void ensureNonPresentValueThrowsExceptionOnEvenNUmberOfElements(){
    final Integer [] elements = {1, 2, 3, 4};

    final BinaryChop<Integer> chop = new BinaryChop<>();
    chop.indexOf(elements, 42);
    fail("Exception expected");
  }
}
