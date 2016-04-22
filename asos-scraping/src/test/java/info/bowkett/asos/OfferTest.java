package info.bowkett.asos;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jbowkett on 22/04/2016.
 */
public class OfferTest {

  @Test
  public void ensureDiscountIsCorrectlyCalculated(){
  final Offer offer = new Offer("", 5, 2, "");
    assertEquals(3, offer.getDiscount(), 0.01);
  }
}
