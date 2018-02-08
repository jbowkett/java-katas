package info.bowkett.katas.bloom;

import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.stream.Stream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.*;
import static org.junit.Assert.assertArrayEquals;

/**
 * Created by jbowkett on 05/08/2014.
 */
public class BloomFilterTest {

  private BloomFilter bloomFilter;

  @Before
  public void setUp(){
    bloomFilter = new BloomFilter();
  }

  @Test
  public void testAdd(){
    boolean exceptionRaised = false;
    try{
      bloomFilter.add("test");
    }
    catch(Exception e){
      exceptionRaised = true;
    }
    assertFalse(exceptionRaised);
  }

  @Test
  public void testContainsTrueWithOneElementInStore(){
    final String testString = "test";
    bloomFilter.add(testString);
    assertTrue(bloomFilter.contains(testString));
  }

  @Test
  public void testAddAll(){
    final String [] inputDictionary = {"one", "two"};
    Stream<String> allDictionary = Arrays.stream(inputDictionary);
    bloomFilter.addAll(allDictionary);
  }

  @Test
  public void testAddWorksWhenThereWillBeAClash(){
    bloomFilter = new BloomFilter(1,1);
    final String [] inputDictionary = {"one", "two"};
    Stream<String> allDictionary = Arrays.stream(inputDictionary);
    bloomFilter.addAll(allDictionary);
  }

  @Test
  public void testLargeValueForKThrowsIllegalArgument(){
    boolean ex = false;
    try{
      bloomFilter = new BloomFilter(1, 17);
    }
    catch(IllegalArgumentException iae){
      ex = true;
    }
    assertTrue(ex);
  }


  @Test
  public void testAtLeastOneOfTheElementsInAClashIsReportedAsPresent(){
    bloomFilter = new BloomFilter(1,1);
    final String [] inputDictionary = {"one", "two"};
    Stream<String> allDictionary = Arrays.stream(inputDictionary);
    bloomFilter.addAll(allDictionary);

    assertTrue(bloomFilter.contains("one") || bloomFilter.contains("two"));
  }

  @Test
  public void testContainsDoesNotReturnTrueForSomethingThatDefinitelyIsNotInThere(){
    //Bloom filters never yield a false negative
    assertFalse(bloomFilter.contains("something that wasn't put in there"));
  }

  @Test
  public void testComputeHash(){
    final int firstHash = bloomFilter.computeNthHashFor("input", 1);
    assertEquals(22389097, firstHash);
  }

  @Test
  public void testByteConversionToIntOnMaxValue(){
    byte[] bytes = toByteArray(Integer.MAX_VALUE);
    assertEquals(Integer.MAX_VALUE, bloomFilter.toInt(bytes));
  }

  @Test
  public void testByteConversionToIntOnZero(){
    byte[] bytes = toByteArray(0);
    assertEquals(0, bloomFilter.toInt(bytes));
  }


  @Test
  public void testGetNthRegion(){
    final byte[] world = {1,2,3,4,  5,6,7,8,  9,10,11,12,  13,14,15,16};
    byte[] region = bloomFilter.getNthRegion(world, 2);
    assertArrayEquals(new byte[]{9, 10, 11, 12}, region);
  }



  protected static byte[] toByteArray(int value) {
    final byte[] bytes = new byte[4];
    ByteBuffer.wrap(bytes).putInt(value);
    return bytes;
  }

}
