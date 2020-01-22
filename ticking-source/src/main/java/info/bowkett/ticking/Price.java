package info.bowkett.ticking;
/**
 * Created by jbowkett on 29/03/2016.
 */
public class Price {
  public final String isin;
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

    Price other = (Price) o;

    return isin.equals(other.isin) &&
      Double.compare(bid, other.bid) == 0 &&
      Double.compare(ask, other.ask) == 0 &&
      Double.compare(mid, other.mid) == 0;

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
