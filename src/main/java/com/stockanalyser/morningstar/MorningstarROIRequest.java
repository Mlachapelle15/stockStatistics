package com.stockanalyser.morningstar;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;

/**
 * Created by mlachapelle on 2017-03-13.
 */
public class MorningstarROIRequest {
  public static final String ROI_HOST_TICKER_PREFIX = "http://quote.morningstar.ca/Quicktakes/AjaxProxy.ashx?url=http%3A//performance.morningstar.com/PerformanceHtml/stock/trailing-total-returns.action%3Ft%3DXTSE%3A";
  public static final String ROI_HOST_TICKER_SUFFIX = "%26s%3D%26ndec%3D2%26ep%3Dtrue%26align%3Dd%26annlz%3Dtrue%26cur%3DCAD%26region%3DCAN%26culture%3Den-CA%26ops%3Dclear%26productCode%3DCAN";

  //private static Map<String, Stock> getQuotes(String ticker) throws URISyntaxException, IOException {
  public static MorningstarStockROI getQuotes(String ticker) throws URISyntaxException, IOException {

    String parsedTicker = (ticker.split("\\.")[0]).replace('-', '.');// TODO: 2017-03-13 make it parameterisable
    String url = ROI_HOST_TICKER_PREFIX + parsedTicker + ROI_HOST_TICKER_SUFFIX;

    Document document = Jsoup.parse(new URL(url).openStream(), "UTF-8", "http://quote.morningstar.ca");

    Elements tableElements = document.select("table");

    Elements tableRowElements = tableElements.select(":not(thead) tr");

    List<String> tableHeader = Lists.newArrayList();
    List<String> tableData = Lists.newArrayList();
    for (int i = 0; i < tableRowElements.size(); i++) {
      Element row = tableRowElements.get(i);
      Elements rowTitle = row.select("th");
      if (rowTitle.text().startsWith("Total Return")) {
        tableHeader = rowTitle.stream().map(Element::text).collect(Collectors.toList());
        tableHeader.remove(0);
      } else if (rowTitle.text().startsWith(parsedTicker)) {
        tableData = row.select("td").stream().map(Element::text).collect(Collectors.toList());
      }
    }

    String roi1y = tableData.get(tableHeader.indexOf("1-Year"));
    String roi5y = tableData.get(tableHeader.indexOf("5-Year"));
    String roi10y = tableData.get(tableHeader.indexOf("10-Year"));


    return new MorningstarStockROI(roi1y, roi5y, roi10y);
  }
}
