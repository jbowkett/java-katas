package info.bowkett.katas.linkedlist;



/**
 * Created by IntelliJ IDEA.
 * User: jbowkett
 * Date: Aug 12, 2013
 * Time: 8:06:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class LinkedList {

  private ListItem root = new ListItem();

  public void add(Object value) {
    root.add(value);
  }


  public Object get(int i) {
    return root.get(i, 0);
  }

  class ListItem{
    private Object value;
    private ListItem next;

    public void add(Object value) {
      if(this.value == null){
        this.value = value;
      }
      else{
        if(next == null) next = new ListItem();
        next.add(value);
      }
    }

    private Object get(int i, int currentIndex) {
      if(i == currentIndex ) return value;
      return next.get(i, currentIndex + 1);
    }
  }
}
