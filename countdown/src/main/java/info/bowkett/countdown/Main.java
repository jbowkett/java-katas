package info.bowkett.countdown;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by jbowkett on 26/09/2014.
 */
public class Main {

  public static void main(String[] args) {
    if (args.length != 7){
      usage();
      System.exit(-1);
    }
    else{
      final IntStream map = Arrays.stream(args).mapToInt(Integer::parseInt);
      final int[] commandLineInts = map.toArray();
      final int total = commandLineInts[0];
      final int[] allInputNumbers = Arrays.copyOfRange(commandLineInts, 1, commandLineInts.length);

      System.out.println("Looking to make : ["+total+"], using : "+ Arrays.toString(allInputNumbers));

      final List<Calculation> solutions = new Countdown(new Calculator()).
          calculate(total, allInputNumbers);

      System.out.println("Found " + solutions.size()+" solutions.");
      solutions.stream().forEach(System.out::println);
    }
  }

  private static void usage() {
    System.out.println("countdown.Main <total to find> <#1> <#2> <#3> <#4> <#5> <#6>");
  }
}
