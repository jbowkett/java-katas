package info.bowkett.katas.cgol;

import static java.lang.System.out;

class GridPrinter {


  void print(Grid grid) {
    final int size = grid.getSize();
    int count = 0;
    for (Cell cell : grid) {
      if(count++ % size == 0){
        out.println();
      }
      out.print(cell.state.toString);
    }
  }
}
