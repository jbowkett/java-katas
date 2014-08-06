package info.bowkett.katas.anagrams;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by jbowkett on 05/08/2014.
 */
public class AnagramList {


  private List<String> anagrams = new ArrayList<>();

  private int size() {
    return anagrams.size();
  }

  private void add(String anagram) {
    anagrams.add(anagram);
  }

  @Override
  public String toString() {
    return anagrams.toString();
  }

  public static Collection<AnagramList> anagramsIn(Stream<String> seedStream) {
    final Map<String, AnagramList> seenStrings = new HashMap<>();
    seedStream.forEach(s -> {
      final char[] seedChars = s.toCharArray();
      Arrays.sort(seedChars);
      final String key = new String(seedChars);
      final AnagramList anagramList = seenStrings.get(key) == null ? new AnagramList() : seenStrings.get(key);
      anagramList.add(s);
      seenStrings.put(key, anagramList);
    });
    final Collection<AnagramList> listsOfAnagrams = seenStrings.values();
    listsOfAnagrams.removeIf(anagramList -> anagramList.size() == 1);
    return listsOfAnagrams;
  }
}
