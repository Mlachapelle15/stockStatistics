package com.stockanalyser.stock.morningstar;

import static org.apache.http.HttpVersion.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.http.client.utils.URIBuilder;

/**
 * Created by mlachapelle on 2017-03-13.
 */
public class MorningstarQuotesRequest {
  public static final String QUOTES_HOST = "financials.morningstar.com/ajax/exportKR2CSV.html";

  //private static Map<String, Stock> getQuotes(String ticker) throws URISyntaxException, IOException {
  public static Morningstar.MorningstarStock getQuotes(String ticker) throws URISyntaxException, IOException {

    URI uri = new URIBuilder().setHost(QUOTES_HOST)
        .setScheme(HTTP)
        .addParameter("callback", "?")// TODO: is it really needed
        .addParameter("t", "XTSE:" + (ticker.split("\\.")[0]).replace('-', '.')) // TODO: 2017-03-13 make it parameterisable
        .addParameter("region", "ca")
        .addParameter("culture", "en-CA")
        .addParameter("cur", "CAD")
        .addParameter("order", "desc")
        .build();

    CSVParser csvParser = new CSVParser(new InputStreamReader(new URL(uri.toString()).openStream()), CSVFormat.DEFAULT);
    List<CSVRecord> csvRecordList = csvParser.getRecords();

    Map<String, List<String>> parsedCsv = new HashMap<String, List<String>>();
    if (csvRecordList != null) {
      for (CSVRecord csvRecord : csvRecordList) {
        List<String> groups = new ArrayList<String>();
        String user = csvRecord.get(0);

        user = user.replaceAll("^\"|\"$", "");

        int i = csvRecord.size();

        for (int j = 1; j < i; j++) {
          String group = csvRecord.get(j);
          if (group != null && !group.isEmpty()) {
            group = group.replaceAll("^\"|\"$", "");
            groups.add(group);
          }
        }
        parsedCsv.put(user, groups);
      }
    }
    csvParser.close();

    return new Morningstar.MorningstarStock(parsedCsv);
  }
}
