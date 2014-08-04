package info.bowkett.katas.fizzbuzz;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * Created by jbowkett on 04/08/2014.
 */
public class FizzBuzzDecisionTest {

  @Test
  public void ensureDivisibleBy3IsFizz(){
    assertEquals("Fizz", new FizzBuzzDecision().forValue(3) );
  }

  @Test
  public void ensureDivisibleBy5IsFizz(){
    assertEquals("Buzz", new FizzBuzzDecision().forValue(5) );
  }

  @Test
  public void ensureDivisibleBy3And5IsFizzBuzz(){
    assertEquals("FizzBuzz", new FizzBuzzDecision().forValue(15) );
  }

  @Test
  public void ensureDivisibleByNeither3Nor5IsNull(){
    assertNull(new FizzBuzzDecision().forValue(7));
  }



}
