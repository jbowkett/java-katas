package info.bowkett.asos;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import static java.nio.file.StandardOpenOption.CREATE;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by jbowkett on 25/04/2016.
 */
public class HTMLOffersPrinter implements OffersPrinter {
  private final File file;
  private final String prefix;

  public HTMLOffersPrinter(File file, String prefix) {
    this.file = file;
    this.prefix = prefix;
  }

  @Override
  public void print(List<Offer> offers) {
    final Document doc = Document.createShell(prefix);
    doc.outputSettings().charset("UTF-8");
    printHeader(doc);
    printOffers(doc, offers);
    saveFile(doc);
  }

  private void printOffers(Document doc, List<Offer> offers) {
    final Element body = doc.body();
    final Element ul = newChildTo(body, "UL");
    for (Offer offer : offers) {
      final Element li = newChildTo(ul, "LI");
      addImg(offer, li);
      addLink(offer, li);
      final Element priceDesc = addRRP(offer, li);
      addNowPrice(offer, priceDesc);
    }
  }

  private void addNowPrice(Offer offer, Element priceDesc) {
    final Element nowPriceDesc = newChildTo(priceDesc, "span").appendText(" Now : £ ");
    final Element sale = newChildTo(nowPriceDesc, "span").addClass("salePrice");
    sale.appendText(""+ offer.salePrice);
    newChildTo(nowPriceDesc, "span").appendText(" (saving : "+offer.getDiscountPercent()+"%)");
  }

  private Element addRRP(Offer offer, Element li) {
    final Element priceDesc = newChildTo(li, "span").appendText(" Was : £ ");
    final Element rrp = newChildTo(priceDesc, "span").addClass("rrp");
    rrp.appendText(""+offer.rrp);
    return priceDesc;
  }

  private void addImg(Offer offer, Element li) {
    newChildTo(li, "img").addClass("productImg").attr("src", offer.imgLink);
  }

  private void addLink(Offer offer, Element li) {
    final Element link = newChildTo(li, "a").addClass("productLink").attr("href", prefix + offer.productLink);
    newChildTo(link, "span").addClass("description").appendText(offer.description);
  }

  private Element newChildTo(Element ul, String li) {
    return ul.appendElement(li);
  }

  private void printHeader(Document doc) {
    doc.title("Here are today's offers");
  }

  private void saveFile(Document doc) {
    try ( final OutputStream os = Files.newOutputStream(file.toPath(), CREATE) ) {
      final String html = doc.html().replaceAll("£", "&pound;");
      os.write(html.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
