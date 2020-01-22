package info.bowkett.log.analysis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Extraction {
  private String pattern;
  private final SimpleDateFormat format;
  private Date previousTimestamp;
  private String previousIdentifier;

  public Extraction(String pattern) {
    this.pattern = pattern;
    format = new SimpleDateFormat(pattern);
  }

  public Timing extract(String inputLine) throws ParseException {
    final String [] parts = inputLine.split("DEBUG|ERROR|WARN|FATAL|INFO");
//    System.out.println("parts = " + Arrays.toString(parts));
    final String datePart = parts[0];
    final String message = parts[1];
    final String currentIdentifier = extractIdentifier(message);
    final Date currentTimestamp = extractTimeStamp(datePart);
    final long timeDifference = extractTimeDifference(currentTimestamp);
    storeCurrentLine(currentIdentifier, currentTimestamp);
    return new Timing(timeDifference, this.previousIdentifier);
  }

  private String extractIdentifier(String message) {
    final String [] parts =  message.split(":");
    return parts[1];
  }

  private void storeCurrentLine(String currentIdentifier, Date currentTimestamp) {
    previousTimestamp = currentTimestamp;
    previousIdentifier = currentIdentifier;
  }

  private long extractTimeDifference(Date currentTimestamp) {
    return (this.previousTimestamp == null) ? -1 :
      (currentTimestamp.getTime() - previousTimestamp.getTime());
  }

  private Date extractTimeStamp(String datePart) throws ParseException {
    return format.parse(datePart);
  }
}
