package com.stockanalyser;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.stockanalyser.morningstar.Morningstar;
import com.stockanalyser.morningstar.MorningstarQuotesRequest;
import com.stockanalyser.morningstar.MorningstarROIRequest;
import com.stockanalyser.morningstar.MorningstarStockROI;
import com.stockanalyser.stock.StockRepository;
import com.stockanalyser.stock.model.Stock;

import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockDividend;
import yahoofinance.quotes.stock.StockStats;

@Component
@EnableConfigurationProperties
public class AppRunner implements ApplicationRunner {
  @Autowired
  private StockRepository repository;

  @Autowired
  private TickerListConfig tickerListConfig;

  @Override
  public void run(ApplicationArguments applicationArguments) throws Exception {
    tickerListConfig.getList().parallelStream().forEach(ticker -> {
      try {
        yahoofinance.Stock stock = YahooFinance.get(ticker);
        StockStats stats = stock.getStats();
        StockDividend dividend = stock.getDividend();

        // stock.getHistory() TODO: this may be helpful in the future

        Morningstar.MorningstarStock morningstarStock = MorningstarQuotesRequest.getQuotes(ticker);
        MorningstarStockROI roi = MorningstarROIRequest.getQuotes(ticker);

        // TODO: 2017-03-28 refactor the constructor
        Stock newStock = new Stock(stock.getSymbol(),
            stock.getName(),
            stock.getQuote().getPrice(),
            stats.getPe(),
            stats.getPeg(),
            dividend.getAnnualYieldPercent(),
            stats.getEps(),
            stats.getROE(),
            stats.getMarketCap(),
            stats.getOneYearTargetPrice(),
            stats.getEBITDA(),
            stats.getShortRatio(),
            stats.getBookValuePerShare(),
            morningstarStock.getDividendGrowth5y(),
            morningstarStock.getDividendGrowth10y(),
            morningstarStock.getPayoutRatio(),
            morningstarStock.getEps(),
            morningstarStock.getEpsGrowth5y(),
            morningstarStock.getEpsGrowth10y(),
            morningstarStock.getFcf(),
            morningstarStock.getFcfGrowth5y(),
            morningstarStock.getFcfGrowth10y(),
            roi.getRoi1y(),
            roi.getRoi5y(),
            roi.getRoi10y(),
            morningstarStock.getRevenueGrowth5y(),
            morningstarStock.getDividendGrowth10y());

        repository.save(newStock);
      } catch (IOException e) {
        System.err.println(e);
      } catch (URISyntaxException e) {
        System.err.println(e);
      }catch (Exception e){
        System.err.println(e);
      }
    });

    repository.findAll().stream().forEach(t -> System.out.println(t));
  }
}
