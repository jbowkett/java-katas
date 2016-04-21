package info.bowkett.asos;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jbowkett on 21/04/2016.
 */
public class SorterTest {

  private List<Offer> offers;
  private Offer largest;
  private Offer smallest;
  private Sorter sorter;

  @Before
  public void setUp(){
    smallest = new Offer("", 100, 99, "");
    final Offer middlest = new Offer("", 100, 50, "");
    largest = new Offer("", 100, 10, "");
    offers = new ArrayList<>();
    offers.add(smallest);
    offers.add(middlest);
    offers.add(largest);
    sorter = new Sorter();
  }

  @Test
  public void ensureSorterFirstItemHasBiggestDiscount(){
    final List<Offer> sortedList = sorter.sort(offers);
    assertEquals(largest, sortedList.get(0));
  }

  @Test
  public void ensureSorterLastItemHasSmallestDiscount(){
    final List<Offer> sortedList = sorter.sort(offers);
    assertEquals(smallest, sortedList.get(2));
  }
}
