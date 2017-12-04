package info.bowkett.katas.cgol;

import static info.bowkett.katas.cgol.Cell.State.Alive;
import static info.bowkett.katas.cgol.Cell.State.Dead;
import java.util.List;
import java.util.stream.Collectors;

public class Cell {
  public Cell() {
  }

  public State tick(List<Cell> neighbouringCells) {
    final List<Cell> aliveNeighbours = neighbouringCells.stream()
                                    .filter(cell -> cell.state == Alive)
                                    .collect(Collectors.toList());
    if(state == Alive){
      switch(aliveNeighbours.size()){
        case 0: case 1: return Dead;
        case 2: case 3: return Alive;
        case 4: case 5: case 6: case 7: case 8: return Dead;
      }
    }
    else{
      switch(aliveNeighbours.size()){
        case 0: case 1: case 2: return Dead;
        case 3: return Alive;
        case 4: case 5: case 6: case 7: case 8: return Dead;
      }
    }
    return null;
  }

  public enum State {
    Dead(". "), Alive("X ");

    public final String toString;

    State(String toString) {
      this.toString = toString;
    }
  }

  public State state = Dead;

}
