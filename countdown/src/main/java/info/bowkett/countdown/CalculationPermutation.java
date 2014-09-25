package info.bowkett.countdown;

/**
 * Created by jbowkett on 25/09/2014.
 */
public class CalculationPermutation {
  public static enum Operator {
    MINUS    ("-"),
    DIVIDE   ("/"),
    MULTIPLY ("*"),
    PLUS     ("+");

    private String toString;
    private Operator(String toString){
      this.toString = toString;
    }
    public String toString(){
      return toString;
    }
  }
  private final NumberPermutation numberNumberPermutation;

  private final Operator[] operations;

  public CalculationPermutation(NumberPermutation numberPermutation, Operator...operations) {
    this.numberNumberPermutation = numberPermutation;
    this.operations = operations;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CalculationPermutation[ ");

    final int[] numbers = numberNumberPermutation.getNumbers();
    int operatorIndex = 0;
    for (int number : numbers) {
      sb.append(number);

      if (operatorIndex < operations.length) {
        sb.append(' ').append(operations[operatorIndex++]).append(' ');
      }
    }
    sb.append(" ]");
    return sb.toString();
  }
}
