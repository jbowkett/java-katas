package info.bowkett.katas.anagrams;

import java.util.Arrays;

import static java.util.Arrays.*;

/**
 * Created by jbowkett on 05/08/2014.
 */
public class Anagram {
  public boolean equal(String s1, String s2) {
    final char[] s1Chars = s1.toCharArray();
    sort(s1Chars);
    final char[] s2Chars = s2.toCharArray();
    sort(s2Chars);
    return Arrays.equals(s1Chars, s2Chars);
  }
}
