package info.bowkett.asos;

import org.junit.Before;
import org.junit.Test;

import static java.util.stream.Collectors.*;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by jbowkett on 21/04/2016.
 */
public class OriginalPriceFilterTest {

  private List<Offer> standardOffers;
  private Predicate<Offer> rrpIsWithinBounds;

  @Before
  public void setup(){
    standardOffers = standardOffers();
    rrpIsWithinBounds = new OriginalPriceFilter(2, 200);
  }

  @Test
  public void ensureFilterRemovesItemsWhereOriginalPriceIsGreaterThanThreshold(){
    final Offer greaterThanThreshold = new Offer("", 500, 350, "", "");
    standardOffers.add(greaterThanThreshold);
    final List<Offer> filteredList = applyFilter(rrpIsWithinBounds);
    assertFalse(filteredList.contains(greaterThanThreshold));
    assertEquals(3, filteredList.size());
  }

  @Test
  public void ensureFilterRemovesItemsWhereOriginalPriceIsLowerThanThreshold(){
    final Offer lowerThanThreshold = new Offer("", 1, 1, "", "");
    standardOffers.add(lowerThanThreshold);
    final List<Offer> filteredList = applyFilter(rrpIsWithinBounds);
    assertFalse(filteredList.contains(lowerThanThreshold));
    assertEquals(3, filteredList.size());
  }


  private List<Offer> applyFilter(Predicate<Offer> filter) {
    return standardOffers.stream()
    .filter(filter)
    .collect(toList());
  }

  private List<Offer> standardOffers() {
    final List<Offer> standardList = new ArrayList<>();
    standardList.add(new Offer("", 10, 5, "", ""));
    standardList.add(new Offer("", 6, 3, "", ""));
    standardList.add(new Offer("", 20, 15, "", ""));
    return standardList;
  }
}
