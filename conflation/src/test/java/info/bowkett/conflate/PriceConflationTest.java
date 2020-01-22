package info.bowkett.conflate;

import info.bowkett.ticking.Price;
import info.bowkett.ticking.PriceFactory;
import info.bowkett.ticking.PricePublisher;
import org.junit.jupiter.api.Test;
import org.mockito.verification.VerificationMode;

import static org.mockito.Mockito.*;

public class PriceConflationTest {

  //this has to have a ticking source and a conflater

  @Test
  void ensureTheTickingSourceSendsPricesToTheConflater() {
    final Conflater<Price> conflater = mock(Conflater.class);
    final PricePublisher publisher =
      new PricePublisher(new PriceConflation(conflater),
        new PriceFactory("GB12345678", 2.4, 3.2));
    publisher.publishPriceForRandomInstrument();
    verify(conflater, onceOnly()).addValue(any(Price.class));
  }

  private VerificationMode onceOnly() {
    return times(1);
  }
}
