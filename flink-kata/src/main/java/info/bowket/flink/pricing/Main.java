package info.bowket.flink.pricing;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jbowkett on 30/03/2016.
 */
public class Main {

  public static void main(String[] args) throws IOException {
    if(args.length != 1){
      throw new IllegalArgumentException("no file specified");
    }
    final String filename = args[0];
    final PricerConfig config = new PricerConfig();
    final List<PriceFactory> priceFactories = config.getPriceFactories(new File(filename));
    final KafkaPriceSink priceSink = new KafkaPriceSink();

    final ExecutorService service = Executors.newFixedThreadPool(priceFactories.size());

    for (PriceFactory priceFactory : priceFactories) {
      final PricePublisher pricePublisher = new PricePublisher(priceSink, priceFactory);
      service.submit((Callable) () -> {
        while(true){
          pricePublisher.publishPriceForRandomInstrument();
        }
      });
    }

    //create a bunch of publisher threads
    //inject into each of them a list of PriceFactories
    //  (each with their own ISIN) they should publish for
    // each thread hands off to a sink of some description

  }
}
