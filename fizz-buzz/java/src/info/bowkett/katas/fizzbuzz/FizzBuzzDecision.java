package info.bowkett.katas.fizzbuzz;

/**
 * Created by jbowkett on 04/08/2014.
 */
public class FizzBuzzDecision {

  public String forValue(int value) {
    final StringBuilder toReturn = new StringBuilder();
    if(value % 3 == 0){
      toReturn.append("Fizz");
    }
    if(value % 5 == 0){
      toReturn.append("Buzz");
    }
    return toReturn.toString();
  }
}
