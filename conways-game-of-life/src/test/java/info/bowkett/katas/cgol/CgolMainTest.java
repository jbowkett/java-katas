package info.bowkett.katas.cgol;

import info.bowkett.katas.cgol.Grid.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by jbowkett on 07/11/2017.
 */
@DisplayName("Game of Life Main test")
class CgolMainTest {

  @Mock
  private final Grid grid = mock(Grid.class);

  @Test
  void ensureTheGridIsIteratedOnTick(){
    when(grid.iterator()).thenReturn(new ArrayList<Cell>().iterator());
    final CgolMain main = new CgolMain(grid);
    main.tick();
    verify(grid).iterator();
  }
  @Test
  void ensureEachCellIsVisitedWithItsNeighboursOnTick(){

    final List<Cell> cells = List.of(mock(Cell.class, "topLeft"),
      mock(Cell.class, "topRight"),
      mock(Cell.class, "bottomRight"),
      mock(Cell.class, "bottomLeft"));

    final Row [] rows = { new Row(cells.get(0), cells.get(1)),
                          new Row(cells.get(2), cells.get(3)) };
    final Grid g = new Grid(2, rows);
    any(List.class);
    final CgolMain main = new CgolMain(g);
    main.tick();
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
      assertThat(argument, containsInAnyOrder(expectedCells));
      return true;
    });
  }
}
