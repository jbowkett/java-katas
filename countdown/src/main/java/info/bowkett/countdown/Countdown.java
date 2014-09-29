package info.bowkett.countdown;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by jbowkett on 24/09/2014.
 */
public class Countdown {
  private final Calculator calc;
  private final CalculationFactory calculationFactory;

  public Countdown(Calculator calc, CalculationFactory calculationFactory) {
    this.calc = calc;
    this.calculationFactory = calculationFactory;
  }

  public List<Calculation> calculate(int total, int [] allNumbers) {

    final List<NumberPermutation> numberPermutations = getNumberPermutations(allNumbers);
    final Stream<Calculation> calculations =
        numberPermutations
            .parallelStream()
            .flatMap(np -> calculationFactory.getCalculationsFor(np).stream());

    final Stream<Calculation> allSolutions =
        calculations.parallel().filter(
            cp -> {
              try{
                return total == calc.solve(cp);
              }
              catch(ArithmeticException ae){
                //on division result yields floating point
                return false;
              }
            }
        );
    final List<Calculation> solutions = new ArrayList<>();
    allSolutions.forEach(solutions::add);
    return solutions;
  }

  List<NumberPermutation> getNumberPermutations(int[] allNumbers) {
    final List<NumberPermutation> numberPermutations = new ArrayList<>();
    for (int i = 0; i < allNumbers.length; i++) {
      final int first = allNumbers[i];
      numberPermutations.add(new NumberPermutation(first));

      for (int j = 0; j < allNumbers.length; j++) {
        if (i == j) continue;
        final int second = allNumbers[j];
        numberPermutations.add(new NumberPermutation(first, second));

        for (int k = 0; k < allNumbers.length; k++) {
          if (j == k || i == k) continue;
          final int third = allNumbers[k];
          numberPermutations.add(new NumberPermutation(first, second, third));

          for (int l = 0; l < allNumbers.length; l++) {
            if (l == k || l == j || l == i) continue;
            final int fourth = allNumbers[l];
            numberPermutations.add(new NumberPermutation(first, second, third, fourth));

            for (int m = 0; m < allNumbers.length; m++) {
              if (m == l || m == k || m == j || m == i) continue;
              final int fifth = allNumbers[m];
              numberPermutations.add(new NumberPermutation(first, second, third, fourth, fifth));

              for (int n = 0; n < allNumbers.length; n++) {
                if (n == m || n == l || n == k || n == j || n == i) continue;
                final int sixth = allNumbers[n];
                numberPermutations.add(new NumberPermutation(first, second, third, fourth, fifth, sixth));
              }
            }
          }
        }
      }
    }
    return numberPermutations;
  }

  public List<Calculation> calculate(int total, int firstNumber, int secondNumber, int thirdNumber, int fourthNumber, int fifthNumber, int sixthNumber) {
    final int[] allNumbers = {firstNumber, secondNumber, thirdNumber, fourthNumber, fifthNumber, sixthNumber};
    return calculate(total, allNumbers);
  }
}
