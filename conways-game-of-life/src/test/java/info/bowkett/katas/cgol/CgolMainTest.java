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
  @Mock
  private final GridPrinter printer = mock(GridPrinter.class);

  @Test
  void ensureTheGridIsIteratedOnTick(){
    when(grid.iterator()).thenReturn(new ArrayList<Cell>().iterator());
    final CgolMain main = new CgolMain(grid, printer);
    main.tick();
    verify(grid).tick();
  }

  @Test
  void ensureGridIsPrintedOnTick(){
    final CgolMain main = new CgolMain(grid, printer);
    main.tick();
    verify(printer).print(grid);
  }
}
