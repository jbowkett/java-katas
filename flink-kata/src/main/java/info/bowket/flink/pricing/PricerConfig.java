package info.bowket.flink.pricing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jbowkett on 31/03/2016.
 */
public class PricerConfig {

  public List<PriceFactory> getPriceFactories(Stream<String> configLines) {
    return configLines.map(line -> {
      final String[] components = line.split(" ");
      throwIfInvalidLine(components);
      return toPriceFactory(components);
    }).collect(Collectors.<PriceFactory>toList());
  }

  public List<PriceFactory> getPriceFactories(File f) throws IOException {
    return getPriceFactories(Files.lines(f.toPath()));
  }

  private PriceFactory toPriceFactory(String[] components) {
    final String isin = components[0];
    final Double midSeed = Double.parseDouble(components[1]);
    final Double maxSpread = Double.parseDouble(components[2]);
    return new PriceFactory(isin, midSeed, maxSpread);
  }

  private void throwIfInvalidLine(String[] components) {
    if(components.length != 3) {
      throw new IllegalArgumentException("Invalid line :" + Arrays.toString(components));
    }
  }
}
