package info.bowkett.katas.cgol;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static info.bowkett.katas.cgol.Cell.*;
import static info.bowkett.katas.cgol.Cell.State.Alive;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CellTest {


  @ParameterizedTest
  @CsvSource({
    "Alive, 0, Dead", //underpopulation
    "Alive, 1, Dead", //underpopulation
    "Alive, 2, Alive",
    "Alive, 3, Alive",
    "Alive, 4, Dead", //overpopulation
    "Alive, 5, Dead", //overpopulation
    "Alive, 6, Dead", //overpopulation
    "Alive, 7, Dead", //overpopulation
    "Alive, 8, Dead", //overpopulation
    "Dead,  0, Dead",
    "Dead,  1, Dead",
    "Dead,  2, Dead",
    "Dead,  3, Alive", //reproduction
    "Dead,  4, Dead",
    "Dead,  5, Dead",
    "Dead,  6, Dead",
    "Dead,  7, Dead",
    "Dead,  8, Dead"})
  void ensureStatesAreCorrect(State inputState, int aliveNeighbours,
                              State expectedOutputState){
    final Cell toTest = new Cell();
    toTest.state = inputState;
    final State resultingState = toTest.tick(createNeighbours(aliveNeighbours));
    assertThat(resultingState, is(expectedOutputState));
  }

  private List<Cell> createNeighbours(int aliveNeighbours) {
    final Cell [] neighbours = new Cell[8];
    Arrays.setAll(neighbours, (i) -> new Cell());
    for (int i = 0; i < aliveNeighbours; i++) {
      neighbours[i].state = Alive;
    }
    final List<Cell> list = Arrays.asList(neighbours);
    Collections.shuffle(list);
    return list;
  }
}
