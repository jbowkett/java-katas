package info.bowkett.countdown;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jbowkett on 24/09/2014.
 */
public class Countdown {
  public Solution calculate(int total, int firstNumber, int secondNumber, int thirdNumber, int fourthNumber, int fifthNumber, int sixthNumber) {

    final int [] allNumbers = {firstNumber, secondNumber, thirdNumber, fourthNumber, fifthNumber, sixthNumber};

    final List<Permutation> permutations = getPermutations(allNumbers);

    return null;
  }

  List<Permutation> getPermutations(int[] allNumbers) {
    final List<Permutation> permutations = new ArrayList<>();
    for (int i = 0; i < allNumbers.length; i++) {
      final int first = allNumbers[i];

      System.out.println(first);
      permutations.add(new Permutation(first));

      for (int j = 0; j < allNumbers.length; j++) {

        if(i == j) continue;

        final int second = allNumbers[j];

        permutations.add(new Permutation(first, second));
        System.out.println(first + " , " + second);

        for (int k = 0; k < allNumbers.length; k++) {

          if (j == k || i == k) continue;

          final int third = allNumbers[k];

          permutations.add(new Permutation(first, second, third));
          System.out.println(first + " , " + second + " , " + third);

          for (int l = 0; l < allNumbers.length; l++) {

            if(l == k || l == j || l == i) continue;

            final int fourth = allNumbers[l];

            permutations.add(new Permutation(first, second, third, fourth));
            System.out.println(first + " , " + second + " , " + third + " , " + fourth);

            for (int m = 0; m < allNumbers.length; m++) {

              if(m == l || m == k || m == j || m == i) continue;

              final int fifth = allNumbers[m];

              permutations.add(new Permutation(first, second, third, fourth, fifth));
              System.out.println(first + " , " + second + " , " + third + " , " + fourth + " , " + fifth);

              for (int n = 0; n < allNumbers.length; n++) {
                if(n == m || n == l || n == k || n == j || n == i) continue;

                final int sixth = allNumbers[n];

                permutations.add(new Permutation(first, second, third, fourth, fifth, sixth));
                System.out.println(first + " , " + second + " , " + third + " , " + fourth + " , " + fifth + " , " + sixth);
              }
            }
          }
        }
      }
    }
    return permutations;
  }
}
