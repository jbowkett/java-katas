package info.bowkett.conflate;

import info.bowkett.ticking.Price;
import info.bowkett.ticking.PriceSink;

public class PriceConflation implements PriceSink, Conflation<Price> {
  private Conflater<Price> conflater;

  public PriceConflation(Conflater<Price> conflater) {
    this.conflater = conflater;
  }

  @Override
  public void add(Price p) {
    conflater.addValue(p);
  }

  @Override
  public Price get() {
    return conflater.getConflatedValue();
  }

  @Override
  public void close() {

  }
}
