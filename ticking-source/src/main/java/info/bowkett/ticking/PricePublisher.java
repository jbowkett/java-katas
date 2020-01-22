package info.bowkett.ticking;

import java.util.Random;

/**
 * Created by jbowkett on 31/03/2016.
 */
public class PricePublisher {
  private final PriceSink priceSink;
  private final PriceFactory[] priceFactories;
  private final Random randomIndex = new Random();

  public PricePublisher(PriceSink priceSink, PriceFactory... priceFactories) {
    this.priceSink = priceSink;
    this.priceFactories = priceFactories;
  }

  public void publishPriceForRandomInstrument() {
    final Price price = priceFactories[randomIndex()].next();
//    System.out.println("price = " + price);
    priceSink.add(price);
  }

  private int randomIndex() {
    return (int)(randomIndex.nextDouble() * priceFactories.length);
  }
}
