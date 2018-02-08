package info.bowkett.katas.datamunging.common;

import info.bowkett.katas.datamunging.weather.WeatherPoint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by jbowkett on 04/08/2014.
 */
public class InputData<T> {
  private final Function<String, T> mapper;


  public InputData(Function<String, T> mapper) {
    this.mapper = mapper;
  }

  public Stream<T> getPoints(String filename) throws IOException {
    final FileReader rf = new FileReader(filename);
    final BufferedReader br = new BufferedReader(rf);

    final Stream<String> cleanedLines = br.lines().map(l -> l.trim().replaceAll("\\*", ""));
    final Stream<String> filteredLines = cleanedLines.filter(l -> l.length() > 0 && Character.isDigit(l.charAt(0)));
    return filteredLines.map(mapper);
  }
}
