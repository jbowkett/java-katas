package info.bowkett.katas.anagrams;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
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
    final ConcurrentMap<String, AnagramList> seenStrings = new ConcurrentHashMap<>();
    seedStream.parallel().forEach(s -> {
      final char[] seedChars = s.toCharArray();
      Arrays.sort(seedChars);
      final String key = new String(seedChars);
      // if already in the list - it's an anagram
      seenStrings.putIfAbsent(key, new AnagramList());
      seenStrings.computeIfPresent(key, (k,list) -> {
        list.add(s);
        return list;
      });
    });
    final Collection<AnagramList> listsOfAnagrams = seenStrings.values();
    listsOfAnagrams.removeIf(anagramList -> anagramList.size() == 1);
    return listsOfAnagrams;
  }
}
