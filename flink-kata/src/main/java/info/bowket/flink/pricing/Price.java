package info.bowket.flink.pricing;


/**
 * Created by jbowkett on 29/03/2016.
 */
public class Price {
  private final String isin;
  public final Double bid;
  public final Double ask;
  public final Double mid;

  public Price(String isin, Double bid, Double ask, Double mid) {
    this.isin = isin;
    this.bid = bid;
    this.ask = ask;
    this.mid = mid;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Price price = (Price) o;

    return isin.equals(price.isin) &&
      bid.equals(price.bid) &&
      ask.equals(price.ask) &&
      mid.equals(price.mid);

  }

  @Override
  public int hashCode() {
    int result = isin.hashCode();
    result = 31 * result + bid.hashCode();
    result = 31 * result + ask.hashCode();
    result = 31 * result + mid.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Price['" + isin + '\'' + ", b=" + bid + ", a=" + ask +
      ", m=" + mid + ']';
  }
}
