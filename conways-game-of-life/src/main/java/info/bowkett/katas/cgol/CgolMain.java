package info.bowkett.katas.cgol;

import info.bowkett.katas.cgol.Cell.State;

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
    final Grid g = new Grid(20);
    final GridPrinter printer = new GridPrinter();
    final CgolMain cgolMain = new CgolMain(g, printer);
//    for (int i = 0; i < 100; i++) {
    while(true){
      cgolMain.tick();
      Thread.sleep(500);
      System.out.println();
    }
  }
}
