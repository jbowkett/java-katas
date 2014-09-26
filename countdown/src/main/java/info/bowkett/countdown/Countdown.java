package info.bowkett.countdown;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static info.bowkett.countdown.CalculationPermutation.Operator;

/**
 * Created by jbowkett on 24/09/2014.
 */
public class Countdown {

  public List<CalculationPermutation> calculate(int total, int firstNumber, int secondNumber, int thirdNumber, int fourthNumber, int fifthNumber, int sixthNumber) {

    final int[] allNumbers = {firstNumber, secondNumber, thirdNumber, fourthNumber, fifthNumber, sixthNumber};

    final List<NumberPermutation> numberPermutations = getNumberPermutations(allNumbers);
    final Stream<CalculationPermutation> calculationPermutationStream =
        numberPermutations
            .parallelStream()
            .flatMap(np -> np.getCalculationPermutations().stream());

    final Stream<CalculationPermutation> allMatches = calculationPermutationStream.parallel().filter(
        cp -> {
          final int[] numbers = cp.numberNumberPermutation.getNumbers();
          final Operator[] operations = cp.operations;
          int accumulator = numbers[0];
          for (int i = 1; i < numbers.length; i++) {
            final int number = numbers[i];
            final Operator operator = operations[i - 1];
            switch (operator) {
              case PLUS:     accumulator += number; break;
              case MINUS:    accumulator -= number; break;
              case MULTIPLY: accumulator *= number; break;
              case DIVIDE:   accumulator /= number; break;
            }
          }
          return accumulator == total;
        }
    );

    final List<CalculationPermutation> solutions = new ArrayList<>();
    allMatches.forEach(solutions::add);
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
}
