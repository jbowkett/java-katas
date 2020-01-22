package info.bowkett.conflate;

public interface Conflater<T> {
  void addValue(T i);

  T getConflatedValue();
}
