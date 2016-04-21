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
    final double discount = o1.rrp - o1.salePrice;
    final double percentage = discount / o1.rrp;
    final double discount2 = o2.rrp - o2.salePrice;
    final double percentage2 = discount2 / o2.rrp;
    return Double.compare(percentage2,  percentage);
  }
}
