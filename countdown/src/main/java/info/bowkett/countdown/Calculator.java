package info.bowkett.countdown;

/**
 * Created by jbowkett on 26/09/2014.
 */
public class Calculator {

  public int solve(Calculation calculation) {
    final int[] numbers = calculation.getNumberNumberPermutation().getNumbers();
    final Calculation.Operator[] operations = calculation.getOperations();
    int accumulator = numbers[0];
    for (int i = 1; i < numbers.length; i++) {
      final int number = numbers[i];
      final Calculation.Operator operator = operations[i - 1];
      switch (operator) {
        case PLUS:     accumulator += number; break;
        case MINUS:    accumulator -= number; break;
        case MULTIPLY: accumulator *= number; break;
        case DIVIDE:
          final double result = (double)accumulator / (double)number;
          if(Math.floor(result) != Math.ceil(result)){
            throw new ArithmeticException("Floating Point division not supported in countdown");
          }
          accumulator = (int)result;
      }
    }
    return accumulator;
  }
}
