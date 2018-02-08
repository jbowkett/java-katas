package info.bowkett.katas.anagrams;

import info.bowkett.katas.anagrams.LimitedAnagramList;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created by jbowkett on 28/01/15.
 */
public class LimitedAnagramListTest {
  @Test
  public void testSomething() throws FileNotFoundException {
    final LimitedAnagramList lal = new LimitedAnagramList(6);
    final FileReader inputFile = new FileReader("" +
      "../Kata06-Anagrams/input-data/wordlist.txt");
    final BufferedReader reader = new BufferedReader(inputFile);
    lal.readIn(reader.lines());
  }

  @Test
  public void testRestrictionLeavesOnlyOneStringInAnagramList(){
    final LimitedAnagramList list = new LimitedAnagramList(6);
    final String [] strings = {"1234", "12345", "654321"};
    list.readIn(Arrays.stream(strings));
    assertEquals(1, list.size());
  }
}
