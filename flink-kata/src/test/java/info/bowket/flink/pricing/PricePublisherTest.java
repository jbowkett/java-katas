package info.bowket.flink.pricing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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

  @Before
  public void executeBeforeEachTestMethod(){
    this.pricePublisher = new PricePublisher(priceSink, firstPriceFactory, secondPriceFactory);
  }

  @Test
  public void ensurePublishPricesCallsThroughToAllPriceFactories(){
    pricePublisher.publishPrices();
    verify(firstPriceFactory).next();
    verify(secondPriceFactory).next();
  }

  @Test
  public void ensurePricePublisherSendsPriceToSink(){
    final Price p = new Price("GB012345678", 40.0, 44.0, 42.0);
    when(firstPriceFactory.next()).thenReturn(p);
    pricePublisher.publishPrices();
    verify(priceSink).addPrice(p);
  }
}
