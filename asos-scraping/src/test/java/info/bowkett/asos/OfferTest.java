package info.bowkett.asos;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jbowkett on 22/04/2016.
 */
public class OfferTest {

  @Test
  public void ensureDiscountIsCorrectlyCalculated(){
  final Offer offer = new Offer("", 5, 2, "", "");
    assertEquals(3, offer.getDiscount(), 0.01);
  }

  @Test
  public void ensureDiscountPercentIsCorrectlyCalculated(){
  final Offer offer = new Offer("", 6, 3, "", "");
    assertEquals(50.0, offer.getDiscountPercent(), 0.01);
  }

  @Test
  public void ensureDiscountPercentIsCorrectlyCalculatedForSmallDiscounts(){
  final Offer offer = new Offer("", 10, 4, "", "");
    assertEquals(60.0, offer.getDiscountPercent(), 0.01);
  }
}
