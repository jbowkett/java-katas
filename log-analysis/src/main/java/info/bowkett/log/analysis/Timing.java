package info.bowkett.log.analysis;

public class Timing {
  private final long timeDifferenceMillis;
  private final String identifier;

  public Timing(long timeDifferenceMillis, String identifier) {
    this.timeDifferenceMillis = timeDifferenceMillis;
    this.identifier = identifier;
  }

  public long getMillis() {
    return timeDifferenceMillis;
  }

  public String getIdentifier() {
    return identifier;
  }
}
