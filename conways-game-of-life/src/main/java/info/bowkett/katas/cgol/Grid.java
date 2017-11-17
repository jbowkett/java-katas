package info.bowkett.katas.cgol;

import java.util.*;

/**
 * Created by jbowkett on 07/11/2017.
 */
public class Grid implements Iterable<Cell>{

  private final Row[] rows;
  private final int gridSize;

  public Grid(int size) {
    this(size, new Row[size]);
    Arrays.setAll(rows, row -> new Row(gridSize, row));
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

  public List<Cell> getSurroundingCellsTo(Cell cell) {
    final Coordinate result = find(cell);
    final Cell topLeft     = get(result.row - 1, result.column - 1);
    final Cell top         = get(result.row - 1, result.column + 0);
    final Cell topRight    = get(result.row - 1, result.column + 1);
    final Cell right       = get(result.row + 0, result.column + 1);
    final Cell bottomRight = get(result.row + 1, result.column + 1);
    final Cell bottom      = get(result.row + 1, result.column + 0);
    final Cell bottomLeft  = get(result.row + 1, result.column - 1);
    final Cell left        = get(result.row + 0, result.column - 1);

    return listFromNonNullElements(left, bottomLeft, bottom, bottomRight,
      right, topRight, top, topLeft);
  }

  private List<Cell> listFromNonNullElements(Cell... cells) {
    final List<Cell> toReturn = new ArrayList<>();
    for (Cell cell : cells) {
      if(cell != null){
        toReturn.add(cell);
      }
    }
    return toReturn;
  }

  Coordinate find(Cell cell) {
    for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
      Row row = rows[rowIndex];
      for (int columnIndex = 0; columnIndex < row.cells.length; columnIndex++) {
        final Cell cellInGrid = row.cells[columnIndex];
        if(cell.equals(cellInGrid)){
          return new Coordinate(rowIndex, columnIndex);
        }
      }
    }
    throw new NoSuchElementException("Cannot find cell:["+cell+"]");
  }

   static class Row{
    private final Cell[] cells;
    Row(int size, int row) {
      this(new Cell[size]);
      Arrays.setAll(cells, column -> new Cell(row, column));
    }
    Row(Cell...cells){
      this.cells = cells;
    }
    private Cell cellAt(int columnIndex) {
      return cells[columnIndex];
    }
  }

  static class Coordinate {
    private final int row, column;
    Coordinate(int row, int column) {
      this.row = row;
      this.column = column;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Coordinate that = (Coordinate) o;

      if (row != that.row) return false;
      return column == that.column;
    }

    @Override
    public int hashCode() {
      int result = row;
      result = 31 * result + column;
      return result;
    }
  }
}
