package info.bowkett.asos;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static java.lang.Double.parseDouble;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jbowkett on 25/04/2016.
 */
public class OffersPrinterTest {

  private static final String ASOS_COM_LINK_PREFIX = "http://www.asos.com";
  private List<Offer> offers;
  private List<PrintedOffers.PrintedOffer> printedOffers;

  @Test
  public void ensureAllOffersArePrinted() throws IOException {
    given_theOffers(
      new Offer("firstThing", 10.00, 5, "/thing.html"),
      new Offer("secondThing", 100.00, 75.00, "/otherThing.html"),
      new Offer("ThirdThing", 105.00, 20.00, "/thirdThing.html")
    );
    when_theOffersArePrinted();
    assertEquals(3, printedOffers.size());
  }

  @Test
  public void ensurePrintedOfferIsEquivalentToInput() throws IOException {
    final Offer theOffer = new Offer("firstThing", 10.00, 5, "/thing.html");
    given_theOffers(theOffer);
    when_theOffersArePrinted();
    then_theOffersAreTheSame(theOffer, printedOffers.get(0));
  }

  private void then_theOffersAreTheSame(Offer expected, PrintedOffers.PrintedOffer printedOffer) {
    assertEquals(expected.description, printedOffer.description);
    assertEquals(ASOS_COM_LINK_PREFIX + expected.link, printedOffer.link);
    assertEquals(expected.rrp, printedOffer.rrp);
    assertEquals(expected.salePrice, printedOffer.salePrice);
  }

  private void given_theOffers(Offer... offers) {
    this.offers = Arrays.asList(offers);
  }

  private void when_theOffersArePrinted() throws IOException {
    final File file = File.createTempFile("asos-parser-output", ".html");
//    Runtime.getRuntime().exec("open "+file);
    final OffersPrinter printer = new HTMLOffersPrinter(file, ASOS_COM_LINK_PREFIX);
    printer.print(offers);
    this.printedOffers = new PrintedOffers(file).asList();
  }

  private static class PrintedOffers {

    private final Document parsedDoc;

    public PrintedOffers(File file) throws IOException {
      parsedDoc = Jsoup.parse(file, "UTF-8");
    }

    public List<PrintedOffer> asList() {
      return parsedDoc.select("li")
        .stream()
        .map(PrintedOffer::new)
        .collect(toList());
    }

    private static class PrintedOffer {
      public String description;
      public String link;
      public Double rrp;
      public Double salePrice;

      public PrintedOffer(Element liElement) {
        description = extractDesc(liElement);
        link = extractLink(liElement);
        rrp = extractDouble(liElement, ".rrp");
        salePrice = extractDouble(liElement, ".salePrice");
      }

      private Double extractDouble(Element liElement, String cssQuery) {
        final Element selected = liElement.select(cssQuery).first();
        if(selected == null) return null;
        return parseDouble(clean(selected.text()));
      }

      private String extractDesc(Element liElement) {
        final Element description = liElement.select(".description").first();
        if(description == null) return null;
        return description.text();
      }

      private String extractLink(Element liElement) {
        final Element link = liElement.select(".link").first();
        if(link == null) return null;
        return link.attr("href");
      }

      private String clean(String text) {
        return text.replaceAll("£", "");
      }
    }
  }
}

















