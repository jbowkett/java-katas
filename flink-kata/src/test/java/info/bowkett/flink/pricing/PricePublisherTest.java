package info.bowkett.flink.pricing;

import info.bowkett.ticking.Price;
import info.bowkett.ticking.PriceFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by jbowkett on 30/03/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PricePublisherTest {


  private PricePublisher pricePublisher;
  @Mock
  private PriceFactory firstPriceFactory;
  @Mock
  private PriceFactory secondPriceFactory;
  @Mock
  private PriceSink priceSink;


  @Test
  public void ensurePublishPricesForRandomInstrumentCallsThroughToAllPriceFactoriesAtLeastOnce(){
    this.pricePublisher = new PricePublisher(priceSink, firstPriceFactory, secondPriceFactory);
    for (int i = 0; i < 10; i++) {
      pricePublisher.publishPriceForRandomInstrument();
    }
    verify(firstPriceFactory, atLeastOnce()).next();
    verify(secondPriceFactory, atLeastOnce()).next();
  }

  @Test
  public void ensurePricePublisherSendsPriceToSink(){
    this.pricePublisher = new PricePublisher(priceSink, firstPriceFactory);
    final Price p = new Price("GB012345678", 40.0, 44.0, 42.0);
    when(firstPriceFactory.next()).thenReturn(p);
    pricePublisher.publishPriceForRandomInstrument();
    verify(priceSink).addPrice(p);
  }
}
