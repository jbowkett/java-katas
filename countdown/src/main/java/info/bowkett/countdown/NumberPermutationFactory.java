package info.bowkett.countdown;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jbowkett on 29/09/2014.
 */
public class NumberPermutationFactory {
  public List<NumberPermutation> getNumberPermutations(int[] allNumbers) {
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
