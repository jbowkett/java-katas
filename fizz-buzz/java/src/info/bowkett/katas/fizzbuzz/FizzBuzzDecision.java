package info.bowkett.katas.fizzbuzz;

/**
 * Created by jbowkett on 04/08/2014.
 */
public class FizzBuzzDecision {

  public String forValue(int value) {

    if(value % 3 == 0 && value % 5 == 0){
      return "FizzBuzz";
    }
    else if(value % 3 == 0){
      return "Fizz";
    }
    else if(value % 5 == 0){
      return "Buzz";
    }
    return null;
  }
}
