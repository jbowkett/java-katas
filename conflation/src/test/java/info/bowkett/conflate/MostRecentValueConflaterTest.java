package info.bowkett.conflate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MostRecentValueConflaterTest {
  //this thing has to conflate values together according to a policy
  @Test
  void ensureItStoresTheMostRecentValue(){
    final MostRecentValueConflater<Integer> conflater = new
      MostRecentValueConflater<>();
    final Integer lastValue = 10;
    for (int i = 0; i <= lastValue; i++) {
      conflater.addValue(i);
    }
    assertEquals(lastValue, conflater.getConflatedValue());
  }
}
