package info.bowkett.countdown; 

import org.junit.Test; 
import org.junit.Before;

import static info.bowkett.countdown.CalculationPermutation.*;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/** 
* Calculator Tester. 
* 
* @author <Authors name> 
* @since <pre>Sep 26, 2014</pre> 
* @version 1.0 
*/ 
public class CalculatorTest {

  private Calculator calculator;

  @Before
  public void before(){
    calculator = new Calculator();
  }

  @Test
  public void testDivision(){
    final CalculationPermutation permutation =
        getCalculationPermutation(new int[]{6, 2}, Operator.DIVIDE);
    final int solution = calculator.solve(permutation);
    assertEquals(3, solution);
  }

  @Test(expected=ArithmeticException.class)
  public void testDivisionWithNonWholeResultThrowsException(){
    final CalculationPermutation permutation =
        getCalculationPermutation(new int[]{6, 5}, Operator.DIVIDE);
    calculator.solve(permutation);
  }

  @Test
  public void testAddition(){
    final CalculationPermutation permutation =
        getCalculationPermutation(new int[]{6, 2}, Operator.PLUS);
    final int solution = calculator.solve(permutation);
    assertEquals(8, solution);
  }

  @Test
  public void testSubtraction(){
    final CalculationPermutation permutation =
        getCalculationPermutation(new int[]{6, 2}, Operator.MINUS);
    final int solution = calculator.solve(permutation);
    assertEquals(4, solution);
  }

  @Test
  public void testSubtractionYieldsNegativeResult(){
    final CalculationPermutation permutation =
        getCalculationPermutation(new int[]{6, 12}, Operator.MINUS);
    final int solution = calculator.solve(permutation);
    assertEquals(-6, solution);
  }

  @Test
  public void testMultiplication(){
    final CalculationPermutation permutation =
        getCalculationPermutation(new int[]{6, 2}, Operator.MULTIPLY);
    final int solution = calculator.solve(permutation);
    assertEquals(12, solution);
  }

  private CalculationPermutation getCalculationPermutation(int[] numbers, Operator multiply) {
    final CalculationPermutation permutation = mock(CalculationPermutation.class, RETURNS_DEEP_STUBS);
    when(permutation.getNumberNumberPermutation().getNumbers()).thenReturn(numbers);
    when(permutation.getOperations()).thenReturn(new Operator[] {multiply});
    return permutation;
  }
}
