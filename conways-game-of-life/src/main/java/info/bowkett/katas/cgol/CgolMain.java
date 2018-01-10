package info.bowkett.katas.cgol;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jbowkett on 07/11/2017.
 */
public class CgolMain {
  private Grid grid;
  private GridPrinter printer;

  public CgolMain(Grid grid, GridPrinter printer) {
    this.grid = grid;
    this.printer = printer;
  }

  public void tick() {
    grid.tick();
    printer.print(grid);
  }

  public static void main(String[] args) throws InterruptedException {
    final ExecutorService executorService = Executors.newFixedThreadPool(20);
    final Grid g = new Grid(20, executorService);
    final GridPrinter printer = new GridPrinter();
    final CgolMain cgolMain = new CgolMain(g, printer);
    final long before = System.nanoTime();
    for (int i = 0; i < 10_000; i++) {
      cgolMain.tick();
      System.out.println();
    }
    final long duration = System.nanoTime() - before;
    System.out.println("Time taken = "+MILLISECONDS.convert(duration,
      NANOSECONDS));
    executorService.shutdown();
  }
}
