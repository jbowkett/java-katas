package info.bowkett.asos;

import java.util.function.Predicate;

/**
 * Created by jbowkett on 22/04/2016.
 */
public class PercentageDiscountThresholdFilter implements Predicate<Offer> {

  private double lowerBound;

  public PercentageDiscountThresholdFilter(double lowerBound) {
    this.lowerBound = lowerBound;
  }

  @Override
  public boolean test(Offer offer) {
    return offer.getDiscountPercent() > lowerBound;
  }
}
