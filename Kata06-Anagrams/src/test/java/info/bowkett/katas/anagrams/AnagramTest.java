package info.bowkett.katas.anagrams;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by jbowkett on 05/08/2014.
 */
public class AnagramTest {
  private Anagram anagram;

  @Before
  public void setup(){
    anagram = new Anagram();
  }

  @Test
  public void testKinship_pinkish(){
    assertTrue(anagram.equal("kinship", "pinkish"));
  }

  @Test
  public void testEnlistInlets(){
    assertTrue(anagram.equal("enlist","inlets"));
  }

  @Test
  public void testListenInlets(){
    assertTrue(anagram.equal("listen","inlets"));
  }

  @Test
  public void testSilentInlets(){
    assertTrue(anagram.equal("silent","inlets"));
  }
}
