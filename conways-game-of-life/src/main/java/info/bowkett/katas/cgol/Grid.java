package info.bowkett.katas.cgol;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import java.util.*;

/**
 * Created by jbowkett on 07/11/2017.
 */
public class Grid implements Iterable<Cell>{

  private final Row[] rows;
  private final int gridSize;

  public Grid(int size) {
    this(size, new Row[size]);
    Arrays.setAll(rows, row -> new Row(gridSize));
  }

  Grid(int size, Row [] rows){
    gridSize = size;
    this.rows = rows;
  }

  @Override
  public Iterator<Cell> iterator() {
    return new Iterator<> (){
      int rowIndex = 0;
      int columnIndex = 0;
      @Override
      public boolean hasNext() {
        return rowIndex < gridSize && columnIndex < gridSize;
      }

      @Override
      public Cell next() {
        final Cell cell = get(rowIndex, columnIndex++);
        if(columnIndex == gridSize){
          columnIndex = 0;
          rowIndex++;
        }
        return cell;
      }
    };
  }

  public Cell get(int row, int column) {
    if(row < 0 || row >= this.gridSize || column < 0 || column >= this.gridSize){
      return null;
    }
    return rows[row].cellAt(column);
  }

  List<Cell> getSurroundingCellsTo(int row, int column) {
    final Cell topLeft     = get(row - 1, column - 1);
    final Cell top         = get(row - 1, column + 0);
    final Cell topRight    = get(row - 1, column + 1);
    final Cell right       = get(row + 0, column + 1);
    final Cell bottomRight = get(row + 1, column + 1);
    final Cell bottom      = get(row + 1, column + 0);
    final Cell bottomLeft  = get(row + 1, column - 1);
    final Cell left        = get(row + 0, column - 1);

    return listFromNonNullElements(left, bottomLeft, bottom, bottomRight,
      right, topRight, top, topLeft);
  }

  private List<Cell> listFromNonNullElements(Cell... cells) {
    return stream(cells).filter(Objects::nonNull).collect(toList());
  }

  public void tick() {
    for (int i = 0; i < rows.length; i++) {
      Row row = rows[i];
      final Cell[] cells = row.cells;
      for (int j = 0; j < cells.length; j++) {
        Cell cell = cells[j];
        cell.tick(getSurroundingCellsTo(i, j));
      }
    }
  }

  static class Row{
    private final Cell[] cells;
    Row(int size) {
      this(new Cell[size]);
      Arrays.setAll(cells, column -> new Cell());
    }
    Row(Cell...cells){
      this.cells = cells;
    }
    private Cell cellAt(int columnIndex) {
      return cells[columnIndex];
    }
  }
}
