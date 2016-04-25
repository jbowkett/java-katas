package info.bowkett.asos;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jbowkett on 21/04/2016.
 */
public class InDiscountOrder implements Comparator<Offer> {
  public List<Offer> sort(List<Offer> offers) {
    return offers
      .stream()
      .sorted(this::compare)
      .collect(Collectors.toList());
  }

  @Override
  public int compare(Offer o1, Offer o2) {
    return Double.compare(o2.getDiscount(),  o1.getDiscount());
  }
}
