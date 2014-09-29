package info.bowkett.countdown;

import java.util.*;

import static info.bowkett.countdown.Calculation.*;
import static info.bowkett.countdown.Calculation.Operator.*;

/**
 * Created by jbowkett on 24/09/2014.
 */
public class NumberPermutation  {
  private final int[] numbers;
  public NumberPermutation(int... numbers) {
    this.numbers = numbers;
  }

  public int[] getNumbers() {
    return numbers;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final NumberPermutation that = (NumberPermutation) o;

    return Arrays.equals(numbers, that.numbers);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(numbers);
  }
}
