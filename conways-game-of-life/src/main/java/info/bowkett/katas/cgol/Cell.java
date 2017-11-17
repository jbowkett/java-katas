package info.bowkett.katas.cgol;

import static info.bowkett.katas.cgol.Cell.State.Dead;
import java.util.List;

public class Cell {
  public Cell(int row, int column) {
    this.row = row;
    this.column = column;
  }

  public State tick(List<Cell> neighbouringCells) {
    return null;
  }

  public enum State {Dead, Alive}

  public State state = Dead;

  private final int row, column;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Cell cell = (Cell) o;

    if (row != cell.row) return false;
    return column == cell.column;
  }

  @Override
  public int hashCode() {
    int result = row;
    result = 31 * result + column;
    return result;
  }

  @Override
  public String toString() {
    return "Cell{" +
      "state=" + state +
      ", row=" + row +
      ", column=" + column +
      '}';
  }
}
