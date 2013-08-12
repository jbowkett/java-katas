package info.bowkett.katas.linkedlist;



/**
 * Created by IntelliJ IDEA.
 * User: jbowkett
 * Date: Aug 12, 2013
 * Time: 8:06:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class LinkedList<T> {

  private ListItem<T> root = new ListItem<T>();

  public void add(T value) {
    root.add(value);
  }


  public T get(int i) {
    return root.get(i, 0);
  }

  class ListItem<T>{
    private T value;
    private ListItem<T> next;

    public void add(T value) {
      if(this.value == null){
        this.value = value;
      }
      else{
        if(next == null) next = new ListItem<T>();
        next.add(value);
      }
    }

    private T get(int i, int currentIndex) {
      if(i == currentIndex ) return value;
      return next.get(i, currentIndex + 1);
    }
  }
}
