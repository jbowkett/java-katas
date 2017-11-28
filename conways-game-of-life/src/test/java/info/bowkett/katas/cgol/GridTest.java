package info.bowkett.katas.cgol;

import info.bowkett.katas.cgol.Grid.Coordinate;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static info.bowkett.katas.cgol.Cell.State.Dead;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder
  .containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
    final Coordinate result = new Coordinate(0, 0);
    final List<Cell> actualSurroundings = g.getSurroundingCellsTo(0,0);
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
    final List<Cell> actualSurroundings = g.getSurroundingCellsTo(0,0);
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
    final List<Cell> actualSurroundings = g.getSurroundingCellsTo(1, 1);
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
    final List<Cell> actualSurroundings = g.getSurroundingCellsTo(0, 5);
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
    final List<Cell> actualSurroundings = g.getSurroundingCellsTo(5, 5);
    assertEquals(8, actualSurroundings.size());
  }


  @Test
  void ensureEachCellIsVisitedWithItsNeighboursOnTick(){

    final List<Cell> cells = List.of(mock(Cell.class, "topLeft"),
      mock(Cell.class, "topRight"),
      mock(Cell.class, "bottomRight"),
      mock(Cell.class, "bottomLeft"));

    final Grid.Row[] rows = { new Grid.Row(cells.get(0), cells.get(1)),
                          new Grid.Row(cells.get(2), cells.get(3)) };
    final Grid g = new Grid(2, rows);
    g.tick();
    assertAll(
      "Surrounding Cells",
      () -> verify(cells.get(0)).tick(withThelistInAnyOrder(cells.get(1), cells.get(2), cells.get(3))),
      () -> verify(cells.get(1)).tick(withThelistInAnyOrder(cells.get(0), cells.get(2), cells.get(3))),
      () -> verify(cells.get(2)).tick(withThelistInAnyOrder(cells.get(0), cells.get(1), cells.get(3))),
      () -> verify(cells.get(3)).tick(withThelistInAnyOrder(cells.get(0), cells.get(1), cells.get(2)))
    );
  }

  private List<Cell> withThelistInAnyOrder(Cell...expectedCells) {
    return argThat(argument -> {
      MatcherAssert.assertThat(argument, Matchers.containsInAnyOrder(expectedCells));
      return true;
    });
  }


}
