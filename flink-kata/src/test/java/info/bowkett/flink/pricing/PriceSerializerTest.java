package info.bowkett.flink.pricing;

import info.bowkett.ticking.Price;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

/**
 * Created by jbowkett on 04/04/2016.
 */
public class PriceSerializerTest {


  @Test
  public void ensureSerializationIsValidForValidPrice(){
    final Price p = new Price("GB12345678", 0.2, 0.3, 0.25);
    final PriceSerializer serializer = new PriceSerializer();
    final byte[] result = serializer.serialize("topic", p);
    assertArrayEquals("GB12345678|0.2|0.3|0.25".getBytes(), result);
  }
}
