package info.bowkett.asos;

import static java.lang.Double.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jbowkett on 21/04/2016.
 */
public class Sorter {
  public List<Offer> sort(List<Offer> offers) {
    return offers
      .stream()
      .sorted(this::comparison)
      .collect(Collectors.toList());
  }

  private int comparison(Offer o1, Offer o2) {
    return compare(o2.getDiscount(),  o1.getDiscount());
  }
}
