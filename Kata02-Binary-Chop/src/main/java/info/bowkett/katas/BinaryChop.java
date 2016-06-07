package info.bowkett.katas;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Created by jbowkett on 06/06/2016.
 */
public class BinaryChop<T extends Comparable<T>> {

  public int indexOf(T[] elements, T toFind){
    final int index = elements.length /2;

    final T halfway = elements[index];
    if(halfway.equals(toFind)){
      return index;
    }

    if(elements.length == 1){
      throw new NoSuchElementException();
    }

    final boolean halfwayValueIsLessThanSoughtValue = halfway.compareTo(toFind) < 0;
    if(halfwayValueIsLessThanSoughtValue){
      final T [] tail = Arrays.copyOfRange(elements, index, elements.length);
      return index + indexOf(tail, toFind);
    }
    else {
      final T [] head = Arrays.copyOfRange(elements, 0, index);
      return indexOf(head, toFind);
    }
  }
}
