package info.bowkett.asos;

import org.junit.Before;
import org.junit.Test;

import static java.util.stream.Collectors.toList;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by jbowkett on 21/04/2016.
 */
public class PercentageDiscountThresholdFilterTest {

  private List<Offer> standardOffers;
  private Predicate<Offer> rrpIsWithinBounds;
  private Predicate<Offer> isAHeftyDiscount;

  @Before
  public void setup(){
    standardOffers = standardOffers();
    rrpIsWithinBounds = new OriginalPriceFilter(2, 200);
    isAHeftyDiscount = new PercentageDiscountThresholdFilter(20.0);
  }


  @Test
  public void ensureFilterRemovesItemsWhereDiscountPercentIsLowerThanThreshold(){
    final Offer lowerThanThreshold = new Offer("", 100, 82, "", "");
    standardOffers.add(lowerThanThreshold);
    final List<Offer> filteredList = applyFilter(isAHeftyDiscount);
    assertFalse(filteredList.contains(lowerThanThreshold));
    assertEquals(3, filteredList.size());
  }

  @Test
  public void ensureMultipleFiltersCanBeApplied(){
    final Offer lowerThanThresholdDiscount = new Offer("", 100, 82, "", "");
    final Offer lowerThanThresholdRRP = new Offer("", 1, 1, "", "");
    final Offer greaterThanThresholdRRP = new Offer("", 500, 350, "", "");
    standardOffers.add(lowerThanThresholdDiscount);
    standardOffers.add(lowerThanThresholdRRP);
    standardOffers.add(greaterThanThresholdRRP);
    final List<Offer> filteredList = applyFilters(rrpIsWithinBounds, isAHeftyDiscount);
    assertFalse(filteredList.contains(lowerThanThresholdDiscount));
    assertFalse(filteredList.contains(lowerThanThresholdRRP));
    assertFalse(filteredList.contains(greaterThanThresholdRRP));
    assertEquals(3, filteredList.size());
  }

  private List<Offer> applyFilter(Predicate<Offer> filter) {
    return standardOffers.stream()
    .filter(filter)
    .collect(toList());
  }

  private List<Offer> applyFilters(Predicate<Offer> filterOne, Predicate<Offer> filterTwo) {
    return standardOffers.stream()
    .filter(filterOne.and(filterTwo))
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
