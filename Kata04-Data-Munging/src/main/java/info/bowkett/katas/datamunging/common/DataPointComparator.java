package info.bowkett.katas.datamunging.common;

import info.bowkett.katas.datamunging.common.DataPoint;

import java.util.Comparator;

/**
 * Created by jbowkett on 04/08/2014.
 */
public class DataPointComparator implements Comparator<DataPoint> {

  @Override
  public int compare(DataPoint o1, DataPoint o2) {
    return o1.getSpread() - o2.getSpread();
  }
}
