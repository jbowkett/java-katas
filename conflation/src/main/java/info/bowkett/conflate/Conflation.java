package info.bowkett.conflate;

import info.bowkett.ticking.Price;

public interface Conflation<T> {
  public void add(T t);

  T get();
//  public void close();
}
