package info.bowkett.katas.cgol;

import info.bowkett.katas.cgol.Grid.Coordinate;
import org.junit.jupiter.api.Test;

import static info.bowkett.katas.cgol.Cell.State.Dead;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder
  .containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jbowkett on 08/11/2017.
 */
public class GridTest {

  @Test
  void ensureAllElementsOfAGridAreIterated(){
    final int gridSize = 2;
    final Grid g = new Grid(gridSize);
    final Set<Cell> iteratedCells = new HashSet<>();
    for (Cell cell : g) {
      iteratedCells.add(cell);
    }
    assertThat(iteratedCells.size(), equalTo(gridSize * gridSize));
  }
  @Test
  void ensureCanGetTheStateOfACell(){
    final int gridSize = 10;
    final Grid g = new Grid(gridSize);
    assertThat(g.get(0,0).state, equalTo(Dead));
  }
  @Test
  void ensureGetSurroundingCellsReturnsTheCorrectCellsOnOrigin(){
    final int gridSize = 10;
    final Grid g = new Grid(gridSize);
    final Cell origin = g.get(0, 0);
    final List<Cell> expectedSurroundings = new ArrayList<>();
    expectedSurroundings.add(g.get(1,0));
    expectedSurroundings.add(g.get(1,1));
    expectedSurroundings.add(g.get(0,1));
    final List<Cell> actualSurroundings = g.getSurroundingCellsTo(origin);
    assertThat(expectedSurroundings, containsInAnyOrder(actualSurroundings.toArray()));
    assertEquals(3, actualSurroundings.size());
  }
  @Test
  void ensureGetSurroundingCellsReturnsTheCorrectCellsOnFourByFourGridForOrigin(){
    final int gridSize = 2;
    final Grid g = new Grid(gridSize);
    final Cell origin = g.get(0, 0);
    final List<Cell> expectedSurroundings = new ArrayList<>();
    expectedSurroundings.add(g.get(1,0));
    expectedSurroundings.add(g.get(1,1));
    expectedSurroundings.add(g.get(0,1));
    final List<Cell> actualSurroundings = g.getSurroundingCellsTo(origin);
    assertThat(expectedSurroundings, containsInAnyOrder(actualSurroundings.toArray()));
    assertEquals(3, actualSurroundings.size());
  }
  @Test
  void ensureGetSurroundingCellsReturnsTheCorrectCellsOnFourByFourGridForOtherSquare(){
    final int gridSize = 2;
    final Grid g = new Grid(gridSize);
    final Cell origin = g.get(1, 1);
    final List<Cell> expectedSurroundings = new ArrayList<>();
    expectedSurroundings.add(g.get(1,0));
    expectedSurroundings.add(g.get(0,0));
    expectedSurroundings.add(g.get(0,1));
    final List<Cell> actualSurroundings = g.getSurroundingCellsTo(origin);
    assertThat(expectedSurroundings, containsInAnyOrder(actualSurroundings.toArray()));
    assertEquals(3, actualSurroundings.size());
  }

  @Test
  void ensureGetSurroundingCellsReturnsTheCorrectCellsAtEdge(){
    final int gridSize = 10;
    final Grid g = new Grid(gridSize);
    final Cell origin = g.get(0, 5);
    final List<Cell> expectedSurroundings = new ArrayList<>();
    expectedSurroundings.add(g.get(0,5));
    expectedSurroundings.add(g.get(1,5));
    expectedSurroundings.add(g.get(1,6));
    expectedSurroundings.add(g.get(1,6));
    expectedSurroundings.add(g.get(0,6));
    final List<Cell> actualSurroundings = g.getSurroundingCellsTo(origin);
    assertEquals(5, actualSurroundings.size());
  }
  @Test
  void ensureGetSurroundingCellsReturnsTheCorrectCellsInCentre(){
    final int gridSize = 10;
    final Grid g = new Grid(gridSize);
    final Cell origin = g.get(5, 5);
    final List<Cell> expectedSurroundings = new ArrayList<>();
    expectedSurroundings.add(g.get(4,4));
    expectedSurroundings.add(g.get(4,5));
    expectedSurroundings.add(g.get(4,6));
    expectedSurroundings.add(g.get(5,6));
    expectedSurroundings.add(g.get(6,6));
    expectedSurroundings.add(g.get(6,5));
    expectedSurroundings.add(g.get(6,4));
    expectedSurroundings.add(g.get(5,4));
    final List<Cell> actualSurroundings = g.getSurroundingCellsTo(origin);
    assertEquals(8, actualSurroundings.size());
  }
  @Test
  void ensureFindRetrievesTheSameCell(){
    final Grid g = new Grid(100);
    final Cell cell = g.get(50, 50);
    final Coordinate coordinate = g.find(cell);
    assertEquals(new Coordinate(50, 50), coordinate);
  }
}
