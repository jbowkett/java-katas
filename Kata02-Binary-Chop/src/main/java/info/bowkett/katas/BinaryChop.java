package info.bowkett.katas;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Created by jbowkett on 06/06/2016.
 */
public class BinaryChop {

  public int indexOf(Integer[] elements, Integer toFind){
    final int index = elements.length /2;

    final Integer halfway = elements[index];
    if(halfway.equals(toFind)){
      return index;
    }

    if(elements.length == 1){
      throw new NoSuchElementException();
    }


    else if(halfway < toFind){
      final Integer [] tail = Arrays.copyOfRange(elements, index, elements.length);
      return index + indexOf(tail, toFind);
    }
    else {
      final Integer [] head = Arrays.copyOfRange(elements, 0, index);
      return indexOf(head, toFind);
    }
  }
}
