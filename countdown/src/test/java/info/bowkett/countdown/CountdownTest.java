package info.bowkett.countdown;

import org.junit.Test;
import org.junit.Before;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Countdown Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Sep 24, 2014</pre>
 */
public class CountdownTest {

  private Countdown countdown;

  @Before
  public void before() throws Exception {
    countdown = new Countdown(new Calculator(), new CalculationFactory());
  }

  @Test
  public void testWithSmallSolutionSet(){
    final int total = 779;
    final List<Calculation> solution = countdown.calculate(total, 3, 33, 1,2,7,9);
    assertEquals(5, solution.size());
    System.out.println("solution.size() = " + solution.size());
    solution.stream().forEach(cp -> System.out.println(total + " = " + cp));
  }

  @Test
  public void testWithNoSolutionSet(){
    final int total = 779;
    final List<Calculation> solution = countdown.calculate(total, 3, 4, 1,2,6,5);
    assertEquals(0, solution.size());
  }

  @Test
  public void testPermutationsWithSixNumbers(){
    final List<NumberPermutation> numberPermutations = countdown.getNumberPermutations(new int[]{1, 2, 3, 4, 5, 6});
//    1 => 1         1 * 1    = 1
//    2 => 4         2 * 2    = 2 * (F(1)+1)
//    3 => 15        3 * 5    = 3 * (F(2) + 1)
//    4 => 64        4 * 16   = 4 * (F(3) + 1)
//    5 => 325       5 * 65   = 5 * (F(4) + 1)
//    6 => 1956      6 * 326  = 6 * (F(6 - 1) +1)

    System.out.println("function(1) = " + function(1));
    System.out.println("function(2) = " + function(2));
    System.out.println("function(3) = " + function(3));
    System.out.println("function(4) = " + function(4));
    System.out.println("function(5) = " + function(5));
    System.out.println("function(6) = " + function(6));

    assertEquals(function(6), numberPermutations.size());
  }

  private int function(int param){
    if (param == 1){
      return 1;
    }
    else{
      return param * (function(param - 1) + 1);
    }
  }
}
