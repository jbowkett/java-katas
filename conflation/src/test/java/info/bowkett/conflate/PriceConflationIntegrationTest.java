package info.bowkett.conflate;


import info.bowkett.ticking.Price;
import info.bowkett.ticking.PriceFactory;
import info.bowkett.ticking.PricePublisher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class PriceConflationIntegrationTest {

  @Test void ensureItWorksSingleThreaded(){
    final PriceFactory priceFactory = new PriceFactory("TE12345678", 2.45,
      5.2);
    final PriceConflation priceConflation = new PriceConflation(new
      MostRecentValueConflater<>());
    final PricePublisher publisher = new PricePublisher(priceConflation, priceFactory);
    publisher.publishPriceForRandomInstrument();
    final Price p = priceConflation.get();
    assertNotNull(p);
  }

  @Test void
  ensureMostRecentConflaterWithMultiThreadedProducersAndOneConsumerThreadIsThreadsafe() throws InterruptedException {
    final MostRecentValueConflater<Price> conflater = new MostRecentValueConflater<>();

    startAll(pricerThreads(conflater, 10));

    final Thread t = getConsumerThread(conflater, "PriceConsumer");
    t.start();

    Thread.sleep(300_000);

    //also try to share the conflater and use primitive locking

    //lots of threads all ticking together and adding into one Conflation
    // instance
    //ensure the instance is able to run for a while and the conflaters get
    // what they are expecting
    //use some spies here

    // also want some consumer of the conflated prices - so it creates
    // contention between the producer and the consumer who should block
    // until a new value is ready
  }

  @Test void
  ensureMostRecentConflaterWithMultiThreadedProducersAndSeveralConsumerThreadsIsThreadsafe() throws InterruptedException {
    final MostRecentValueConflater<Price> conflater = new MostRecentValueConflater<>();

    startAll(pricerThreads(conflater, 10));

    startAll(consumerThreads(conflater, 10));
    Thread.sleep(300_000);
  }

  @Test void
  ensureAverageConflaterWithMultiThreadedProducersAndSeveralConsumerThreadsIsThreadsafe
    () throws InterruptedException {
    final MeanValueOfLastXValuesConflater conflater = new
      MeanValueOfLastXValuesConflater(5);

    startAll(pricerThreads(conflater, 10));

    startAll(consumerThreads(conflater, 10));
    Thread.sleep(300_000);

  }

  @Test void
  ensureMaxValueConflaterWithMultiThreadedProducersAndSeveralConsumerThreadsIsThreadsafe
    () {
  }

  @BeforeAll
  static void executeOnceBeforeAllTestCases() {
    Thread.setDefaultUncaughtExceptionHandler((t1, e) -> {
      System.out.println(e);
      fail(e);
    });
  }

  private Thread[] consumerThreads(Conflater<Price> conflater, int threadCount) {
    final Thread [] threads = new Thread [threadCount];
    for (int i = 0; i < threadCount; i++) {
      threads[i] = getConsumerThread(conflater, "PriceConsumer::"+i);
    }
    return threads;
  }

  private Thread getConsumerThread(Conflater<Price> conflater, String threadName) {
    final Runnable r = () -> {
      while(true){
        final Price conflatedValue = conflater.getConflatedValue();
        assertNotNull(conflatedValue);
        Thread.yield();
      }
    };
    return new Thread(r, threadName);
  }

  private void startAll(Thread[] threads) {
    for (Thread thread : threads) {
      thread.start();
    }
  }
  private Thread[] pricerThreads(Conflater<Price> conflater, int threadCount) {
    final Thread [] threads = new Thread[threadCount];
    for (int i = 0; i < threadCount; i++) {
      threads[i] = newPricerThreadFor(conflater, "PricerThread::"+i);
    }
    return threads;
  }

  private Thread newPricerThreadFor(Conflater<Price> conflater, String threadName) {
    final PriceConflation priceConflation = new PriceConflation(conflater);
    final PriceFactory priceFactory = new PriceFactory("TE12345678", 2.45,
      5.2);

    final PricerRunnable pr = new PricerRunnable(new PricePublisher
      (priceConflation, priceFactory));

    return new Thread(pr, threadName);
  }
  static final class PricerRunnable implements Runnable{


    private PricePublisher publisher;

    public PricerRunnable(PricePublisher publisher) {
      this.publisher = publisher;
    }

    public void run(){

        while(true){
          publisher.publishPriceForRandomInstrument();
          Thread.yield();
        }
      }

  }
}
