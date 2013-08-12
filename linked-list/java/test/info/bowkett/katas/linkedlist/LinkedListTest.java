package info.bowkett.katas.linkedlist;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: jbowkett
 * Date: Aug 12, 2013
 * Time: 8:06:54 AM
 * To change this template use File | Settings | File Templates.
 */

public class LinkedListTest {

  @Test
  public void tesAdd()throws Exception{
    final LinkedList<String> list = new LinkedList<String>();
    list.add("first Add");
  }

  @Test
  public void addTwoItems()throws Exception{
    final LinkedList<String> list = new LinkedList<String>();
    list.add("first Add");
    list.add("second Add");
  }

  @Test
  public void addTwoItemsAndRetrieve()throws Exception{
    final LinkedList<String> list = new LinkedList<String>();
    list.add("first Add");
    final String secondValue = "second Add";
    list.add(secondValue);
    assertEquals(secondValue, list.get(1));
  }



}
