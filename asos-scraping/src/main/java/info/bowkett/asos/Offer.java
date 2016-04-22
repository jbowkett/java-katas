package info.bowkett.asos;

/**
 * Created by jbowkett on 20/04/2016.
 */
public class Offer {
  final String description;
  final double rrp;
  final double salePrice;
  final String link;

  public Offer(String description, double rrp, double salePrice, String link) {
    this.description = description;
    this.rrp = rrp;
    this.salePrice = salePrice;
    this.link = link;
  }

  @Override
  public String toString() {
    return "Offer{" +
      "description='" + description + '\'' +
      ", rrp=" + rrp +
      ", salePrice=" + salePrice +
      ", link='" + link + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Offer offer = (Offer) o;

    return Double.compare(offer.rrp, rrp) == 0 &&
      Double.compare(offer.salePrice, salePrice) == 0 &&
      description.equals(offer.description) &&
      link.equals(offer.link);

  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = description.hashCode();
    temp = Double.doubleToLongBits(rrp);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(salePrice);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + link.hashCode();
    return result;
  }

  public double getDiscount() {
    return rrp - salePrice;
  }

  public double getDiscountPercent() {
    return getDiscount() / rrp * 100;
  }
}
