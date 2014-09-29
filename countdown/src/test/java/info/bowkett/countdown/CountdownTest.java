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
    countdown = new Countdown(new Calculator(),
                              new CalculationFactory(),
                              new NumberPermutationFactory());
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
}
