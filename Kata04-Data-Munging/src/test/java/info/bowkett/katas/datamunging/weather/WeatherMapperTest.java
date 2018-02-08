package info.bowkett.katas.datamunging.weather;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jbowkett on 04/08/2014.
 */
public class WeatherMapperTest {

  @Test
  public void testValidInput(){
    final WeatherPoint w = WeatherMapper.WEATHER_MAPPER.apply("  20  84    57    71          58.9       0.00 FH      150  6.3 160  13  3.6  90 43 1032.5".trim());
    assertEquals(27, w.getSpread());
  }
}
