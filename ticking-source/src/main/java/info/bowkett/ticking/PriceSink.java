package info.bowkett.ticking;

import info.bowkett.ticking.Price;

/**
 * Created by jbowkett on 31/03/2016.
 */
public interface PriceSink {
  void addPrice(Price p);
  void close();
}
