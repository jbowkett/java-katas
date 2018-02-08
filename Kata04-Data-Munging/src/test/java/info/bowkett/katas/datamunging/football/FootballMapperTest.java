package info.bowkett.katas.datamunging.football;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jbowkett on 04/08/2014.
 */
public class FootballMapperTest {

  @Test
  public void testValidInput(){
    final FootballPoint fp = FootballMapper.FOOTBALL_MAPPER.apply("    3. Manchester_U    38    24   5   9    87  -  45    77".trim());
    assertEquals("Manchester_U", fp.getTeam());
    assertEquals(42, fp.getSpread());
  }
}
