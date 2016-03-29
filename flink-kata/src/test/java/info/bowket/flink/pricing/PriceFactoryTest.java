package info.bowket.flink.pricing;

import junit.framework.AssertionFailedError;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jbowkett on 29/03/2016.
 */
public class PriceFactoryTest {
  private PriceFactory priceFactory;

  @Test
  public void ensurePriceIsCreated() {
    final Price p = priceFactory.next();
    assertNotNull(p);
  }

  @Test
  public void ensureNextPriceIsDifferentToLast() {
    final Price p = priceFactory.next();
    assertFalse(p.equals(priceFactory.next()));
  }

  @Test
  public void ensureNoTenPricesAreTheSame() {
    final Set<Price> prices = new HashSet<>();
    for (int i = 0; i < 10; i++) {
      final Price next = priceFactory.next();
      System.out.println("next = " + next);
      throwIfPriceIsAlreadyPresent(prices, next);
      prices.add(next);
    }
  }

  @Before
  public void executeBeforeEachTestCase() {
    final String testIsin = "US12345678";
    this.priceFactory = new PriceFactory(testIsin, 5.5d, 0.2d);
  }

  @Test
  public void ensureBidIsLowerThanAsk(){
    final Price next = priceFactory.next();
    assertTrue(next.bid < next.ask);
  }

  @Test
  public void ensureMidIsInTheMiddleOfSpread(){
    final Price next = priceFactory.next();
    final double spread = next.ask - next.bid;
    final double expectedMidBasedOnBid = next.bid + spread / 2.0;
    final double expectedMidBasedOnAsk = next.ask - spread / 2.0;
    assertEquals(expectedMidBasedOnBid, next.mid, 0.00000000001);
    assertEquals(expectedMidBasedOnAsk, next.mid, 0.00000000001);
  }

  private void throwIfPriceIsAlreadyPresent(Set<Price> prices, Price next) {
    if (prices.contains(next)) {
      throw new AssertionFailedError("[" + next + "] was previously generated");
    }
  }
}
