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
  private NumberPermutationFactory numberPermutationFactory;

  public Countdown(Calculator calc, CalculationFactory calculationFactory, NumberPermutationFactory numberPermutationFactory) {
    this.calc = calc;
    this.calculationFactory = calculationFactory;
    this.numberPermutationFactory = numberPermutationFactory;
  }

  public List<Calculation> calculate(int total, int [] allNumbers) {

    final List<NumberPermutation> numberPermutations = numberPermutationFactory.getNumberPermutations(allNumbers);
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

  public List<Calculation> calculate(int total, int firstNumber, int secondNumber, int thirdNumber, int fourthNumber, int fifthNumber, int sixthNumber) {
    final int[] allNumbers = {firstNumber, secondNumber, thirdNumber, fourthNumber, fifthNumber, sixthNumber};
    return calculate(total, allNumbers);
  }
}
