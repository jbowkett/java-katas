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
  public final NumberPermutation numberNumberPermutation;

  public final Operator[] operations;

  public CalculationPermutation(NumberPermutation numberPermutation, Operator...operations) {
    this.numberNumberPermutation = numberPermutation;
    this.operations = operations;
  }

  @Override
  public String toString() {
    final int[] numbers = numberNumberPermutation.getNumbers();
    final StringBuilder sb = new StringBuilder(""+numbers[0]);
    for (int i = 1; i < numbers.length ; i++) {
      sb.append(' ').append(operations[i - 1]).append(' ');
      sb.append(numbers[i]);
    }
    return sb.toString();
  }
}
