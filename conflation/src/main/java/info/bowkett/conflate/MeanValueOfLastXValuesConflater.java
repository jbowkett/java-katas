package info.bowkett.conflate;

import info.bowkett.ticking.Price;

import java.util.ArrayList;
import java.util.List;

public class MeanValueOfLastXValuesConflater implements Conflater<Price>{
  private final List<Price> prices;
  private final int valuesCount;

  public MeanValueOfLastXValuesConflater(int valuesCount) {
    prices = new ArrayList<>(valuesCount);
    this.valuesCount = valuesCount;
  }

  public void addValue(Price price) {
    if(prices.size() >= valuesCount){
      prices.remove(0);
    }
    this.prices.add(price);
  }

  @Override
  public Price getConflatedValue() {
    double bidTally = 0;
    double askTally = 0;
    double midTally = 0;
    for (Price price : prices) {
      bidTally += price.bid;
      askTally += price.ask;
      midTally += price.mid;
    }
    return new Price(prices.get(0).isin,
                    bidTally/prices.size(),
                    askTally/prices.size(),
      midTally/prices.size());
  }
}
