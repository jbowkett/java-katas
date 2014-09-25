package info.bowkett.countdown;

import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Permutation Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Sep 25, 2014</pre>
 */
public class NumberPermutationTest {

  @Test
  public void testCalculationPermutationFor_1_Number(){
    final NumberPermutation perm = new NumberPermutation(1);
    final List<CalculationPermutation> calculations = perm.getCalculationPermutations();
    assertEquals(1, calculations.size()); //4^0
  }

  @Test
  public void testCalculationPermutationFor_2_Numbers(){
    final NumberPermutation perm = new NumberPermutation(1, 2);
    final List<CalculationPermutation> calculations = perm.getCalculationPermutations();
    assertEquals(4, calculations.size()); // 4^1
  }

  @Test
  public void testCalculationPermutationFor_3_Numbers(){
    final NumberPermutation perm = new NumberPermutation(1, 2, 3);
    final List<CalculationPermutation> calculations = perm.getCalculationPermutations();
    assertEquals(16, calculations.size()); //4 ^ 2
  }

  @Test
  public void testCalculationPermutationFor_4_Numbers(){
    final NumberPermutation perm = new NumberPermutation(1, 2, 3, 4);
    final List<CalculationPermutation> calculations = perm.getCalculationPermutations();
    assertEquals(64, calculations.size()); //4 ^ 3
  }

  @Test
  public void testCalculationPermutationFor_5_Numbers(){
    final NumberPermutation perm = new NumberPermutation(1, 2, 3, 4, 5);
    final List<CalculationPermutation> calculations = perm.getCalculationPermutations();
    assertEquals(256, calculations.size()); //4 ^ 4
  }

  @Test
  public void testCalculationPermutationFor_6_Numbers(){
    final NumberPermutation perm = new NumberPermutation(1, 2, 3, 4, 5, 6);
    final List<CalculationPermutation> calculations = perm.getCalculationPermutations();
    assertEquals(1024, calculations.size()); //4 ^ 5
  }
}
