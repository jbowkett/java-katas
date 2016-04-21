package info.bowkett.asos;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static java.lang.Double.*;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by jbowkett on 20/04/2016.
 */
public class OffersPageParser {
  public List<Offer> parse(String html) {
    final Document parsedDoc = Jsoup.parse(html);
    final Elements prices = parsedDoc.select("li div.categoryImageDiv");
    return prices.stream()
      .map(this::toOffer)
      .collect(toList());
  }

  private Offer toOffer(Element categoryImageDiv) {
    final Elements productPriceDiv = findProductPriceDiv(categoryImageDiv);
    final double rrp = getRRP(productPriceDiv);
    final double salePrice = getSalePrice(productPriceDiv);

    final Element productLink = findProductLink(categoryImageDiv);
    final String title = productLink.attr("title");
    final String href = productLink.attr("href");
    return new Offer(title, rrp, salePrice, href);
  }

  private double getSalePrice(Elements productPriceDiv) {
    final Elements saleElements = productPriceDiv.select("span.outlet-current-price");
    final Element saleElement = saleElements.get(0);
    final String saleText = saleElement.text();
    final String cleaned = cleanPrice(saleText);
    return parseDouble(cleaned);
  }

  private double getRRP(Elements productPriceDiv) {
    final Elements rrpElements = productPriceDiv.select("span.rrp");
    final Element rrpElement = rrpElements.get(0);
    final String rrpText = rrpElement.text();
    final String cleaned = cleanPrice(rrpText);
    return parseDouble(cleaned);
  }

  private String cleanPrice(String priceText) {
    return priceText.replaceAll("£|RRP ", "");
  }

  private Elements findProductPriceDiv(Element categoryImageDiv) {
    final Element listItem = categoryImageDiv.parent();
    return listItem.select("div.productprice");
  }

  private Element findProductLink(Element element) {
    final Elements productLinks = element.getElementsByAttributeValue("class", "productImageLink");
    return productLinks.get(0);
  }

  private Collector<Offer, ?, List<Offer>> toList() {
    return Collectors.<Offer>toList();
  }
}
