package info.bowkett.ticking;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    final List<PriceFactory> ignoredDueToException = config.getPriceFactories(Arrays.stream(configLines));
  }


  @Test
  public void ensureConfigCanBeReadFromAFileCreatedInTest() throws IOException {
    final File f = createFileOfPriceFactories();
    final PricerConfig config = new PricerConfig();
    final List<PriceFactory> priceFactories = config.getPriceFactories(f);
    assertEquals(2, priceFactories.size());
  }

  @Test
  public void ensureConfigCanBeReadFromAFileOnDisk() throws IOException {
    final File f = new File("/Users/jbowkett/Coding/java-katas/ticking-source/src/test/resources/isins-config.txt");
    final PricerConfig config = new PricerConfig();
    final List<PriceFactory> priceFactories = config.getPriceFactories(f);
    assertEquals(3, priceFactories.size());
  }


  private File createFileOfPriceFactories() throws IOException {
    final File f = File.createTempFile("pricing-factory-", ".config");
    f.deleteOnExit();
    final String [] configLines = new String [] {"GB124345678 22 2", "GB87654321 44 0.2"};
    try(final FileWriter fw = new FileWriter(f);
        final BufferedWriter bw = new BufferedWriter(fw)) {
      for (String configLine : configLines) {
        bw.append(configLine).append('\n');
      }
    }
    return f;
  }
}
