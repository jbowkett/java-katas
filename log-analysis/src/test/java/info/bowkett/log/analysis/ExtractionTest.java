package info.bowkett.log.analysis;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.text.ParseException;

public class ExtractionTest {

  private Extraction extractor;

  @Test
  void ensureItCanExtractTheTimeGivenTwoLines() throws ParseException {
    final String inputLine = "2014-07-02 20:52:39.000 DEBUG className:200 - " +
      "This is debug message";
    final String nextLine = "2014-07-02 20:52:39.100 DEBUG className:201 - " +
      "This is debug message2";
    final Timing noMillisBetweenLogMessages = extractor.extract(inputLine);
    assertEquals(-1, noMillisBetweenLogMessages.getMillis());
    final Timing millisBetweenLogMessages = extractor.extract(nextLine);
    assertEquals(100, millisBetweenLogMessages.getMillis());
  }

  @Test
  void ensureItCanExtractTheEntityForALine() throws ParseException {
    final String inputLine = "2014-07-02 20:52:39.000 INFO trade:12345678";
    final String nextLine = "2014-07-02 20:52:39.100 DEBUG trade:45678";
    final Timing t = extractor.extract(inputLine);
    assertNotNull(t);
    assertEquals("12345678", t.getIdentifier());
    assertEquals(-1, t.getMillis());
    final Timing t2 = extractor.extract(nextLine);
    assertEquals("45678", t2.getIdentifier());
  }

  @BeforeEach
  void setUp() {
    final String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
    this.extractor = new Extraction(pattern);
  }
}
//update the log expression
//grep the logs for the messages
//read the input file

//output the report