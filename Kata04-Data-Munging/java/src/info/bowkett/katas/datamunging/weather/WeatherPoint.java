package info.bowkett.katas.datamunging.weather;

import info.bowkett.katas.datamunging.common.DataPoint;

/**
 * Created by jbowkett on 04/08/2014.
 */
public class WeatherPoint implements DataPoint {
  private final int dayNumber, minTemp, maxTemp;

  public WeatherPoint(int dayNumber, int minTemp, int maxTemp) {
    this.dayNumber = dayNumber;
    this.minTemp = minTemp;
    this.maxTemp = maxTemp;
  }

  @Override
  public int getSpread(){
    return maxTemp - minTemp;
  }

  @Override
  public String toString() {
    return "WeatherPoint{" +
        "dayNumber=" + dayNumber +
        ", minTemp=" + minTemp +
        ", maxTemp=" + maxTemp +
        '}';
  }
}
