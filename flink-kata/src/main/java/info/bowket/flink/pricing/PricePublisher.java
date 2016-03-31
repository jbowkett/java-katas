package info.bowket.flink.pricing;

/**
 * Created by jbowkett on 31/03/2016.
 */
public class PricePublisher {
  private final PriceSink priceSink;
  private final PriceFactory[] priceFactories;

  public PricePublisher(PriceSink priceSink, PriceFactory... priceFactories) {
    this.priceSink = priceSink;
    this.priceFactories = priceFactories;
  }

  public void publishPrices() {
    for (PriceFactory priceFactory : priceFactories) {
      final Price price = priceFactory.next();
      priceSink.addPrice(price);
    }
  }
}
