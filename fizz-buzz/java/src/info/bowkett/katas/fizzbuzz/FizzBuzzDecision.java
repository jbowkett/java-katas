package info.bowkett.katas.fizzbuzz;

/**
 * Created by jbowkett on 04/08/2014.
 */
public class FizzBuzzDecision {

  private static final String FIZZ = "Fizz";
  private static final String BUZZ = "Buzz";

  public String forValue(int value) {
    final StringBuilder toReturn = new StringBuilder();
    if(isDivisibleBy3(value)){
      toReturn.append(FIZZ);
    }
    if(isDivisibleBy5(value)){
      toReturn.append(BUZZ);
    }
    return toReturn.toString();
  }

  private boolean isDivisibleBy3(int value) {
    return value % 3 == 0;
  }

  private boolean isDivisibleBy5(int value) {
    return value % 5 == 0;
  }
}
