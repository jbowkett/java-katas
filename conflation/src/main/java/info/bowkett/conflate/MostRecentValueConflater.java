package info.bowkett.conflate;

public class MostRecentValueConflater<T> implements Conflater<T>{
  private T value;

  @Override
  public void addValue(T i) {
    this.value = i;
  }

  @Override
  public T getConflatedValue() {
    return value;
  }
}
