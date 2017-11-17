package info.bowkett.katas.cgol;

import info.bowkett.katas.cgol.Cell.State;

/**
 * Created by jbowkett on 07/11/2017.
 */
public class CgolMain {
  private Grid grid;

  public CgolMain(Grid grid) {
    this.grid = grid;
  }

  public void tick() {
    for (Cell cell : grid) {
      final State newState = cell.tick(grid.getSurroundingCellsTo(cell));

    }
  }
}
