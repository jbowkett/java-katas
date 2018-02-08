package info.bowkett.katas.anagrams;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Created by jbowkett on 05/08/2014.
 */
public class AnagramListTest {


  @Test
  public void testOneEntryYieldsEmptyCollection(){
    final String [] seedList = {"test"};
    final Stream<String> seedStream = Arrays.stream(seedList);
    assertEquals(0, AnagramList.anagramsIn(seedStream).size());
  }

  @Test
  public void testTwoAnagramsYieldsCollectionContainingOneEntry(){
    final String [] seedList = {"kinship", "pinkish"};
    final Stream<String> seedStream = Arrays.stream(seedList);
    assertEquals(1, AnagramList.anagramsIn(seedStream).size());
  }

  @Test
  public void testTwoStringsThatAreNotAnagramsYieldsEmptyCollection(){
    final String [] seedList = {"kinship", "dave"};
    final Stream<String> seedStream = Arrays.stream(seedList);
    assertEquals(0, AnagramList.anagramsIn(seedStream).size());
  }

  @Test
  public void testInputFile() throws FileNotFoundException {
    final File fileName = new File("input-data/wordlist.txt");
    final FileReader inputFile = new FileReader(fileName);
    final BufferedReader reader = new BufferedReader(inputFile);
    final Collection<AnagramList> anagramsInFile = AnagramList.anagramsIn(reader.lines());
//    anagramsInFile.forEach(anagramList -> System.out.println(anagramList.toString()));
    assertEquals(20683, anagramsInFile.size());
  }
}
