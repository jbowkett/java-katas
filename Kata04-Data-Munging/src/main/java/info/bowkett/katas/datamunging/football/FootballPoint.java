package info.bowkett.katas.datamunging.football;

import info.bowkett.katas.datamunging.common.DataPoint;

/**
 * Created by jbowkett on 04/08/2014.
 */
public class FootballPoint implements DataPoint{

  private final String team;
  private final int goalsFor, goalsAgainst;

  public FootballPoint(String team, int goalsFor, int goalsAgainst) {
    this.team = team;
    this.goalsFor = goalsFor;
    this.goalsAgainst = goalsAgainst;
  }

  @Override
  public int getSpread() {
    return goalsFor - goalsAgainst;
  }

  public String getTeam() {
    return team;
  }

  @Override
  public String toString() {
    return "FootballPoint{" +
        "team='" + team + '\'' +
        ", goalsFor=" + goalsFor +
        ", goalsAgainst=" + goalsAgainst +
        '}';
  }
}
