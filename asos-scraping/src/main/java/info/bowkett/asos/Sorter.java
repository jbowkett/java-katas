package info.bowkett.asos;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jbowkett on 21/04/2016.
 */
public class Sorter {
  public List<Offer> sort(List<Offer> offers) {
    return offers
      .stream()
      .sorted(this::compare)
      .collect(Collectors.toList());
  }

  private int compare(Offer o1, Offer o2) {
    final double percentage = getDiscountPercentge(o1);
    final double percentage2 = getDiscountPercentge(o2);
    return Double.compare(percentage2,  percentage);
  }

  private double getDiscountPercentge(Offer offer) {
    final double discount = offer.rrp - offer.salePrice;
    return discount / offer.rrp;
  }
}
