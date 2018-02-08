package info.bowkett.katas.anagrams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by jbowkett on 28/01/15.
 */
public class LimitedAnagramList {
  private final int anagramLimitSize;
  private final List<String> restrictedAnagramList;

  public LimitedAnagramList(int anagramLimitSize) {
    this.anagramLimitSize = anagramLimitSize;
    restrictedAnagramList = new ArrayList<>();
  }


  public void readIn(Stream<String> lines) {
    lines.filter(l -> l.length() == anagramLimitSize).spliterator()
        .forEachRemaining(restrictedAnagramList::add);
  }

  public int size() {
    return restrictedAnagramList.size();
  }
}
