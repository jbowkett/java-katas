package info.bowkett.asos;

import java.util.function.Predicate;

/**
 * Created by jbowkett on 22/04/2016.
 */
public class OriginalPriceFilter implements Predicate<Offer> {

  private double lowerBound;
  private double upperBound;

  public OriginalPriceFilter(double lowerBound, double upperBound) {
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
  }

  @Override
  public boolean test(Offer offer) {
    return offer.rrp < upperBound && offer.rrp > lowerBound;
  }
}
