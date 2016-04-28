package info.bowkett.asos;

/**
 * Created by jbowkett on 20/04/2016.
 */
public class Offer {
  final String description;
  final Double rrp;
  final Double salePrice;
  final String link;
  private final String imgLink;

  public Offer(String description, double rrp, double salePrice, String productLink, String imgLink) {
    this.description = description;
    this.rrp = rrp;
    this.salePrice = salePrice;
    this.link = productLink;
    this.imgLink = imgLink;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Offer offer = (Offer) o;

    return description.equals(offer.description) &&
      rrp.equals(offer.rrp) &&
      salePrice.equals(offer.salePrice) &&
      link.equals(offer.link) &&
      imgLink.equals(offer.imgLink);

  }

  @Override
  public int hashCode() {
    int result = description.hashCode();
    result = 31 * result + rrp.hashCode();
    result = 31 * result + salePrice.hashCode();
    result = 31 * result + link.hashCode();
    result = 31 * result + imgLink.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Offer{" +
      "description='" + description + '\'' +
      ", rrp=" + rrp +
      ", salePrice=" + salePrice +
      ", link='" + link + '\'' +
      ", imgLink='" + imgLink + '\'' +
      '}';
  }

  public double getDiscount() {
    return rrp - salePrice;
  }

  public double getDiscountPercent() {
    return getDiscount() / rrp * 100;
  }
}
