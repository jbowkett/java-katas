package info.bowket.flink.pricing;

import java.util.Random;

/**
 * Created by jbowkett on 29/03/2016.
 */
public class PriceFactory {

  private final String isin;
  private final double maxSpread;
  private final Random midRandom;
  private final Random spreadRandom;
  private final Random upOrDown;
  private Double lastMid;

  public PriceFactory(String isin, double midSeed, double maxSpread) {
    this.isin = isin;
    this.maxSpread = maxSpread;
    this.midRandom = new Random();
    this.spreadRandom = new Random();
    this.upOrDown = new Random();
    this.lastMid = midSeed;
  }

  public Price next() {
    final Double mid = getNextMid();
    final Double spreadDeviationFromMid = getNextSpreadDeviationFromMid();
    this.lastMid = mid;
    final double ask = mid + spreadDeviationFromMid;
    final double bid = mid - spreadDeviationFromMid;
    return new Price(this.isin, bid, ask, mid);
  }

  private Double getNextSpreadDeviationFromMid() {
    final double spread = spreadRandom.nextDouble() * maxSpread;
    return spread / 2.0;
  }

  private Double getNextMid() {
    final double delta = midRandom.nextDouble() / 10;
    return up() ? lastMid + delta : lastMid - delta;
  }

  private boolean up() {
    return upOrDown.nextBoolean();
  }
}
