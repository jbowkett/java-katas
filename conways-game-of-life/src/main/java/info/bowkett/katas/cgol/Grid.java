package info.bowkett.katas.cgol;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jbowkett on 07/11/2017.
 */
public class Grid implements Iterable<Cell>{

  private Row[] rows;
  private final int gridSize;
  private ExecutorService executorService;

  public Grid(int size, ExecutorService executorService) {
    this(size, new Row[size], executorService);
    initRows(rows);
    initStates();
  }

  private void initStates() {
    final Random random = new Random();
    for (Row row : rows) {
      final Cell[] cells = row.cells;
      for (Cell cell : cells) {
        if(random.nextBoolean()){
          cell.state = Cell.State.Alive;
        }
      }
    }
  }

  Grid(int size, Row[] rows, ExecutorService executorService){
    gridSize = size;
    this.rows = rows;
    this.executorService = executorService;
  }

  private void initRows(Row[] rows) {
    Arrays.setAll(rows, row -> new Row(gridSize));
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
    final Row [] newRows = new Row[gridSize];
    initRows(newRows);

    final CountDownLatch rowsStillProcessing = new CountDownLatch(rows.length);
    for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
      tickRowAsync(newRows[rowIndex], rowsStillProcessing, rowIndex);
    }
    await(rowsStillProcessing);
    this.rows = newRows;
  }
  final AtomicInteger calcCount = new AtomicInteger(0);
  private void tickRowAsync(Row newRow, CountDownLatch rowsStillProcessing, int rowIndex) {
    executorService.execute(() -> {
      final Row existingRow = rows[rowIndex];
      final Cell[] existingCells = existingRow.cells;
      for (int columnIndex = 0; columnIndex < existingCells.length; columnIndex++) {
        final Cell cell = existingCells[columnIndex];
        final Cell.State newState = cell.tick(getSurroundingCellsTo(rowIndex, columnIndex));
        newRow.cellAt(columnIndex).state = newState;
        calcCount.incrementAndGet();
      }
      rowsStillProcessing.countDown();
    });
  }

  private void await(CountDownLatch latch) {
    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  int getSize() {
    return gridSize;
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
