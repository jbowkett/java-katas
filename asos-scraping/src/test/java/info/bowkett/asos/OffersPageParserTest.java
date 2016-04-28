package info.bowkett.asos;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by jbowkett on 20/04/2016.
 */
public class OffersPageParserTest {


  @Test
  public void ensureTheCorrectNumberOfOffersAreReturnedWhenParsingAnExamplePage() throws IOException {
    final String inputFile = readExampleFile();
    final OffersPageParser parser = new OffersPageParser();
    final List<Offer> results = parser.parse(inputFile);
    assertEquals(36, results.size());
  }

  @Test
  public void ensureAnOfferIsRepresentedInTheDomainCorrectlyWhenParsingAnExamplePage() throws IOException {
    final String inputFile = readExampleFile();
    final OffersPageParser parser = new OffersPageParser();
    final List<Offer> results = parser.parse(inputFile);
    assertEquals(new Offer("Pepe Jeans Linda Abstract Print Oversized Shirt",
      75.00, 45.00,
      "/Pepe-Jeans/Pepe-Jeans-Linda-Abstract-Print-Oversized-Shirt/Prod/pgeproduct.aspx?iid=5635421&cid=13516&sh=0&pge=0&pgesize=36&sort=-1&clr=Black&totalstyles=1070&gridsize=3",
      "http://images.asos-media.com/inv/media/1/2/4/5/5635421/black/image1xl.jpg"),
      results.get(0));
  }

  private String readExampleFile() throws IOException {
    final File f = new File("/Users/jbowkett/other/java-katas/asos-scraping/src/test/resources/ladies-offers.htm");
    return new String(Files.readAllBytes(f.toPath()));
  }
}
