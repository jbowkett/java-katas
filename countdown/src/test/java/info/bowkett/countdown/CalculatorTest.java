package info.bowkett.countdown; 

import org.junit.Test; 
import org.junit.Before;

import static info.bowkett.countdown.Calculation.*;
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
    final Calculation permutation =
        getCalculation(new int[]{6, 2}, Operator.DIVIDE);
    final int solution = calculator.solve(permutation);
    assertEquals(3, solution);
  }

  @Test(expected=ArithmeticException.class)
  public void testDivisionWithNonWholeResultThrowsException(){
    final Calculation permutation =
        getCalculation(new int[]{6, 5}, Operator.DIVIDE);
    calculator.solve(permutation);
  }

  @Test
  public void testAddition(){
    final Calculation calculation =
        getCalculation(new int[]{6, 2}, Operator.PLUS);
    final int solution = calculator.solve(calculation);
    assertEquals(8, solution);
  }

  @Test
  public void testSubtraction(){
    final Calculation permutation =
        getCalculation(new int[]{6, 2}, Operator.MINUS);
    final int solution = calculator.solve(permutation);
    assertEquals(4, solution);
  }

  @Test
  public void testSubtractionYieldsNegativeResult(){
    final Calculation permutation =
        getCalculation(new int[]{6, 12}, Operator.MINUS);
    final int solution = calculator.solve(permutation);
    assertEquals(-6, solution);
  }

  @Test
  public void testMultiplication(){
    final Calculation permutation =
        getCalculation(new int[]{6, 2}, Operator.MULTIPLY);
    final int solution = calculator.solve(permutation);
    assertEquals(12, solution);
  }

  private Calculation getCalculation(int[] numbers, Operator multiply) {
    final Calculation permutation = mock(Calculation.class, RETURNS_DEEP_STUBS);
    when(permutation.getNumberNumberPermutation().getNumbers()).thenReturn(numbers);
    when(permutation.getOperations()).thenReturn(new Operator[] {multiply});
    return permutation;
  }
}
