package info.bowket.flink.pricing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jbowkett on 31/03/2016.
 */
public class PricerConfigTest {

  @Test
  public void ensureConfigLinesAreParsedCorrectly(){
    final String [] configLines = new String [] {"GB124345678 22 2", "GB87654321 44 0.2"};
    final PricerConfig config = new PricerConfig();
    final List<PriceFactory> priceFactories = config.getPriceFactories(Arrays.stream(configLines));
    assertEquals(2, priceFactories.size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void ensureInvalidConfigLinesThrowAnException(){
    final String [] configLines = new String [] {"GB124345678 222", "GB87654321 44 0.2"};
    final PricerConfig config = new PricerConfig();
    final List<PriceFactory> priceFactories = config.getPriceFactories(Arrays.stream(configLines));
  }


  @Test
  public void ensureConfigCanBeReadFromAFile() throws IOException {
    final File f = File.createTempFile("pricing-factory-", ".config");
    f.deleteOnExit();
    final String [] configLines = new String [] {"GB124345678 22 2", "GB87654321 44 0.2"};
    try(final FileWriter fw = new FileWriter(f);
        final BufferedWriter bw = new BufferedWriter(fw)) {
      for (String configLine : configLines) {
        bw.append(configLine).append('\n');
      }
    }
    final PricerConfig config = new PricerConfig();
    final List<PriceFactory> priceFactories = config.getPriceFactories(f);
    assertEquals(2, priceFactories.size());
  }
}
