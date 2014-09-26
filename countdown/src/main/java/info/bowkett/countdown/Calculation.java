package info.bowkett.countdown;

/**
 * Created by jbowkett on 25/09/2014.
 */
public class Calculation {
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

  public Calculation(NumberPermutation numberPermutation, Operator... operations) {
    this.numberNumberPermutation = numberPermutation;
    this.operations = operations;
  }

  public NumberPermutation getNumberNumberPermutation() {
    return numberNumberPermutation;
  }

  public Operator[] getOperations() {
    return operations;
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
