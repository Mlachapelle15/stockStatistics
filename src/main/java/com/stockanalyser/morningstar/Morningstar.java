package com.stockanalyser.morningstar;

import static org.apache.http.HttpVersion.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.http.client.utils.URIBuilder;

import yahoofinance.YahooFinance;

/**
 * Created by mlachapelle on 2017-03-12.
 */
public class Morningstar {
  public static final Logger logger = Logger.getLogger(Morningstar.class.getName());
  public static final String QUOTES_HOST = "financials.morningstar.com/ajax/exportKR2CSV.html";

  public static class MorningstarStock {
    private List<BigDecimal> dividends;
    private BigDecimal dividend;
    private BigDecimal dividendGrowth5y;
    private BigDecimal dividendGrowth10y;

    public MorningstarStock() {

    }

    public MorningstarStock(List<String> yearlyDividends) {
      computeDividendStats(yearlyDividends.stream().map(div -> {
        Optional<BigDecimal> parsedValue;
        try {
          parsedValue = Optional.of(new BigDecimal(div));
        } catch (NumberFormatException e) {
          parsedValue = Optional.empty();
        }
        return parsedValue;
      })
          .filter(Optional::isPresent)
          .map(Optional::get)
          .collect(Collectors.toList()));
    }

    private void computeDividendStats(List<BigDecimal> dividends) {
      logger.info("Dividends: " + dividends);
      if (dividends.size() > 0) {
        dividend = dividends.get(0);
        logger.info("Dividend: " + dividend);
        if (dividends.size() >= 5) {
          dividendGrowth5y = (dividends.get(0).min(dividends.get(5)).divide(BigDecimal.valueOf(5), 2)); // TODO: 2017-03-13 Validate that it do what I want
          logger.info("dividendGrowth5y: " + dividendGrowth5y);
          if (dividends.size() >= 10) {
            dividendGrowth10y = (dividends.get(0).min(dividends.get(9)).divide(BigDecimal.valueOf(9), 2)); // TODO: 2017-03-13 Validate that it do what I want
            logger.info("dividendGrowth10y: " + dividendGrowth10y);
          }
        }
      }
    }
  }

  //private static Map<String, Stock> getQuotes(String ticker) throws URISyntaxException, IOException {
  public static void getQuotes(String ticker) throws URISyntaxException, IOException {

    URI uri = new URIBuilder().setHost(QUOTES_HOST)
        .setScheme(HTTP)
        .addParameter("callback", "?")// TODO: is it really needed
        .addParameter("t", "XTSE:" + (ticker.split("\\.")[0]).replace('-', '.')) // TODO: 2017-03-13 make it parameterisable
        .addParameter("region", "ca")
        .addParameter("culture", "en-CA")
        .addParameter("cur", "CAD")
        .addParameter("order", "desc")
        .build();

    URL request = new URL(uri.toString());
    URLConnection connection = request.openConnection();
    connection.setConnectTimeout(YahooFinance.CONNECTION_TIMEOUT);
    connection.setReadTimeout(YahooFinance.CONNECTION_TIMEOUT);
    //InputStreamReader is = new InputStreamReader(connection.getInputStream());
    //BufferedReader br = new BufferedReader(is);

    CSVParser csvParser = new CSVParser(new InputStreamReader(new URL(uri.toString()).openStream()), CSVFormat.DEFAULT);
    List<CSVRecord> csvRecordList = csvParser.getRecords();

    Map<String, List<String>> ret = new HashMap<String, List<String>>();
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
        ret.put(user, groups);
      }
    }
    csvParser.close();

    MorningstarStock morningstarStock = new MorningstarStock(ret.get("Dividends CAD"));// TODO: 2017-03-13 Support mode than dividends CAD
    ArrayList result = new ArrayList();
    /*for(String line = br.readLine(); line != null; line = br.readLine()) {
      if(line.equals("Missing Symbols List.")) {// TODO: 2017-03-13
        YahooFinance.logger.log(Level.SEVERE, "The requested symbol was not recognized by Yahoo Finance");// TODO: 2017-03-13
      } else {
        YahooFinance.logger.log(Level.INFO, "Parsing CSV line: " + Utils.unescape(line)); // TODO: 2017-03-13

        Object data = parseCSVLine(line);
        result.add(data);
      }
    */

  }
/*
    StockQuotesRequest request = new StockQuotesRequest(query);
    List<StockQuotesData> quotes = request.getResult();
    Map<String, Stock> result = new HashMap<String, Stock>();
    for(StockQuotesData data : quotes) {
      Stock s = data.getStock();
      result.put(s.getSymbol(), s);
    }

    if(includeHistorical) {
      for(Stock s : result.values()) {
        s.getHistory();
      }
    }*/

}
