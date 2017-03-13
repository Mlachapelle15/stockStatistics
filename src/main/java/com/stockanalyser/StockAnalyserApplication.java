package com.stockanalyser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.stockanalyser.model.Stock;
import com.stockanalyser.morningstar.Morningstar;

import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockDividend;
import yahoofinance.quotes.stock.StockStats;

@SpringBootApplication
public class StockAnalyserApplication {

  public static void main(String[] args) {
    SpringApplication.run(StockAnalyserApplication.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner(StockRepository repository) {
    return args -> {
      Arrays.asList("TD.TO", "SJ.TO").forEach(ticker -> {
        try {
          yahoofinance.Stock stock = YahooFinance.get(ticker);
          StockStats stats = stock.getStats();
          StockDividend dividend = stock.getDividend();

          // stock.getHistory() TODO: this may be helpful in the future

          repository.save(new Stock(stock.getSymbol(),
              stock.getName(),
              stats.getPe(),
              stats.getPeg(),
              dividend.getAnnualYieldPercent(),
              stats.getEps(),
              stats.getROE(),
              stats.getMarketCap(),
              stats.getOneYearTargetPrice(),
              stats.getEBITDA(),
              stats.getShortRatio(),
              stats.getBookValuePerShare()));

          Morningstar.getQuotes(ticker);
        } catch (IOException e) {
          System.err.println(e);
        } catch (URISyntaxException e) {
          System.err.println(e);
        }
      });

      repository.findAll().stream().forEach(t -> System.out.println(t));
    };
  }
}


