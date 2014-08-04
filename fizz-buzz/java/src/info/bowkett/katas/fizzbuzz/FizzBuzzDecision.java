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

  public static void main(String [] args){
    final FizzBuzzDecision decision = new FizzBuzzDecision();
    for (int i = 1; i <= 100 ; i++){
      final String fb = decision.forValue(i);
      if(fb.length() ==  0){
        System.out.println(i);
      }
      else{
        System.out.println(fb);
      }
    }
  }
}
