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

import com.google.common.collect.Lists;

import yahoofinance.YahooFinance;

/**
 * Created by mlachapelle on 2017-03-12.
 */
public class Morningstar {
  public static final Logger logger = Logger.getLogger(Morningstar.class.getName());
  public static final String QUOTES_HOST = "financials.morningstar.com/ajax/exportKR2CSV.html";

  public static class MorningstarStock {
    private BigDecimal dividend;
    private BigDecimal dividendGrowth5y;
    private BigDecimal dividendGrowth10y;
    private BigDecimal payoutRatio;
    private BigDecimal epsGrowth10y;
    private BigDecimal epsGrowth5y;
    private BigDecimal eps;
    private BigDecimal fcfGrowth10y;
    private BigDecimal fcfGrowth5y;
    private BigDecimal fcf;

    public MorningstarStock() {

    }

    public MorningstarStock(Map<String, List<String>> parsedCsv) {
      List<String> dividendRow = parsedCsv.getOrDefault("Dividends CAD", Lists.newArrayList());
      List<String> payoutRow = parsedCsv.getOrDefault("Payout Ratio % *", Lists.newArrayList());
      List<String> epsRow = parsedCsv.getOrDefault("Earnings Per Share CAD", Lists.newArrayList());
      List<String> fcfRow = parsedCsv.getOrDefault("Free Cash Flow Per Share CAD", Lists.newArrayList());

      computeDividendStats(parseCsvRow(dividendRow));
      computePayoutRatio(parseCsvRow(payoutRow));
      computeEPSStats(parseCsvRow(epsRow));
      computeFCFStats(parseCsvRow(fcfRow));
    }

    // TODO: 2017-03-13 All the parsing should be in a class
    private List<BigDecimal> parseCsvRow(List<String> unparsedCsvRow) {
      return unparsedCsvRow.stream().map(this::stringToBigDecimal)
          .filter(Optional::isPresent)
          .map(Optional::get)
          .collect(Collectors.toList());
    }

    private Optional<BigDecimal> stringToBigDecimal(String value) {
      Optional<BigDecimal> parsedValue;
      try {
        parsedValue = Optional.of(new BigDecimal(value));
      } catch (NumberFormatException e) {
        parsedValue = Optional.empty();
      }
      return parsedValue;
    }

    private void computePayoutRatio(List<BigDecimal> payoutRatios) {
      logger.info("Payout ratios: " + payoutRatios);
      if (!payoutRatios.isEmpty()) {
        this.payoutRatio = payoutRatios.get(0);
      }
    }

    private void computeDividendStats(List<BigDecimal> dividends) {
      logger.info("Dividends: " + dividends);
      if (dividends.size() > 0) {
        dividend = dividends.get(0);
        logger.info("Dividend: " + dividend);
        if (dividends.size() >= 5) {
          compute5yAverage(dividends).ifPresent(divGrowth -> dividendGrowth5y = divGrowth);
          logger.info("dividendGrowth5y: " + dividendGrowth5y);
          if (dividends.size() >= 10) {
            compute10yAverage(dividends).ifPresent(divGrowth -> dividendGrowth10y = divGrowth);
            logger.info("dividendGrowth10y: " + dividendGrowth10y);
          }
        }
      }
    }

    private void computeEPSStats(List<BigDecimal> epsValues) {
      logger.info("EPS: " + epsValues);
      if (epsValues.size() > 0) {
        eps = epsValues.get(0);
        logger.info("EPS: " + eps);
        if (epsValues.size() >= 5) {
          compute5yAverage(epsValues).ifPresent(divGrowth -> epsGrowth5y = divGrowth);
          logger.info("epsGrowth5y: " + epsGrowth5y);
          if (epsValues.size() >= 10) {
            compute10yAverage(epsValues).ifPresent(divGrowth -> epsGrowth10y = divGrowth);
            logger.info("epsGrowth10y: " + epsGrowth10y);
          }
        }
      }
    }

    private void computeFCFStats(List<BigDecimal> fcfValues) {
      logger.info("FCF: " + fcfValues);
      if (fcfValues.size() > 0) {
        fcf = fcfValues.get(0);
        logger.info("FCF: " + fcf);
        if (fcfValues.size() >= 5) {
          compute5yAverage(fcfValues).ifPresent(divGrowth -> fcfGrowth5y = divGrowth);
          logger.info("fcfGrowth5y: " + fcfGrowth5y);
          if (fcfValues.size() >= 10) {
            compute10yAverage(fcfValues).ifPresent(divGrowth -> fcfGrowth10y = divGrowth);
            logger.info("fcfGrowth10y: " + fcfGrowth10y);
          }
        }
      }
    }

    private Optional<BigDecimal> compute5yAverage(List<BigDecimal> values) {
      // TODO: 2017-03-13 Validate that it do what I want
      return values.size() >= 5 ? Optional.of((values.get(0).min(values.get(5)).divide(BigDecimal.valueOf(5), 2))) : Optional.empty();
    }

    private Optional<BigDecimal> compute10yAverage(List<BigDecimal> values) {
      // TODO: 2017-03-13 Validate that it do what I want
      return values.size() >= 10 ? Optional.of((values.get(0).min(values.get(9)).divide(BigDecimal.valueOf(9), 2))) : Optional.empty();
    }

    public BigDecimal getPayoutRatio() {
      return payoutRatio;
    }

    public BigDecimal getDividend() {
      return dividend;
    }

    public BigDecimal getDividendGrowth5y() {
      return dividendGrowth5y;
    }

    public BigDecimal getDividendGrowth10y() {
      return dividendGrowth10y;
    }

    public BigDecimal getEpsGrowth10y() {
      return epsGrowth10y;
    }

    public BigDecimal getEpsGrowth5y() {
      return epsGrowth5y;
    }

    public BigDecimal getEps() {
      return eps;
    }

    public BigDecimal getFcfGrowth10y() {
      return fcfGrowth10y;
    }

    public BigDecimal getFcfGrowth5y() {
      return fcfGrowth5y;
    }

    public BigDecimal getFcf() {
      return fcf;
    }
  }

  //private static Map<String, Stock> getQuotes(String ticker) throws URISyntaxException, IOException {
  public static MorningstarStock getQuotes(String ticker) throws URISyntaxException, IOException {

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

    return new MorningstarStock(parsedCsv);// TODO: 2017-03-13 Support mode than dividends CAD
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
