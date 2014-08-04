package info.bowkett.katas.datamunging.common;

import info.bowkett.katas.datamunging.football.FootballMapper;
import info.bowkett.katas.datamunging.football.FootballPoint;
import info.bowkett.katas.datamunging.weather.WeatherMapper;
import info.bowkett.katas.datamunging.weather.WeatherPoint;

import java.io.*;
import java.util.Comparator;
import java.util.function.Function;

/**
 * Created by jbowkett on 04/08/2014.
 */
public class Main<T> {
  private final InputData<T> reader;
  private Comparator<T> comparator;

  public Main(InputData<T> reader, Comparator<T> comparator) {
    this.reader = reader;
    this.comparator = comparator;
  }

//  open the file
//  create a list of Weather data points
//  sort on spread
//  print the max


  public void printMinAndMaxSpread(String filename) throws IOException {
    final Object[] points = reader.getPoints(filename).sorted(comparator).toArray();
    System.out.println("min = " + points[0]);
    System.out.println("max = " + points[points.length -1]);
  }

  public static void main(String[] args) throws IOException {

    final InputData<WeatherPoint> reader = new InputData<>(WeatherMapper.WEATHER_MAPPER);
    final Main<WeatherPoint> m = new Main(reader, new DataPointComparator());
    m.printMinAndMaxSpread(args[0]);

    final InputData<FootballPoint> reader2 = new InputData<>(FootballMapper.FOOTBALL_MAPPER);
    final Main<WeatherPoint> m2 = new Main(reader2, new DataPointComparator());
    m2.printMinAndMaxSpread(args[1]);




  }
}
