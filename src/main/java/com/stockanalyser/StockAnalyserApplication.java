package com.stockanalyser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.h2.tools.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.stockanalyser.model.Stock;
import com.stockanalyser.morningstar.Morningstar;
import com.stockanalyser.morningstar.MorningstarQuotesRequest;
import com.stockanalyser.morningstar.MorningstarROIRequest;
import com.stockanalyser.morningstar.MorningstarStockROI;

import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockDividend;
import yahoofinance.quotes.stock.StockStats;

@SpringBootApplication
public class StockAnalyserApplication {
  private static final String DBNAME = "test";

  public static void main(String[] args) throws Exception {
    SpringApplication.run(StockAnalyserApplication.class, args);

    // open the in-memory database within a VM

    Class.forName("org.h2.Driver"); // (1)
    Connection conn
        = DriverManager.getConnection("jdbc:h2:mem:" + DBNAME, "sa", "sa"); // (2)
    // username:password are very important and must be used for connecting via H2 Console

    Statement stat = conn.createStatement(); // (3)
    stat.executeUpdate("create table mytbl(id int primary key, name varchar(255))");
    stat.executeUpdate("insert into mytbl values(1, 'Hello')");
    stat.executeUpdate("insert into mytbl values(2, 'World')");

    // Verify that sample data was really inserted
    ResultSet rs = stat.executeQuery("select * from mytbl");
    System.out.println("ResultSet output:");
    while (rs.next()) {
      System.out.println("> " + rs.getString("name"));
    }

    // start a TCP server
    Server server = Server.createTcpServer().start(); // (4)
    // .. use in embedded mode ..

    // or use it from another process:
    System.out.println("Server started and connection is open.");
    System.out.println("URL: jdbc:h2:" + server.getURL() + "/mem:" + DBNAME);

    System.out.println("\n");
    System.out.println(
        "now start the H2 Console in another process using:\n" +
            "$ cd h2/bin; java -cp h2-1.4.185.jar org.h2.tools.Console -web -browser");

    System.out.println("Press [Enter] to stop.");
    System.in.read();

    System.out.println("Stopping server and closing the connection");

    rs.close();
    server.stop();
    conn.close();
    System.out.println("Server is STOPPED");
  }

  @Bean
  public CommandLineRunner commandLineRunner(StockRepository repository) {
    return args -> {
      Arrays.asList("TD.TO", "SJ.TO", "ZCL.TO", "SNC.TO", "EMP-A.TO").forEach(ticker -> {
        try {
          yahoofinance.Stock stock = YahooFinance.get(ticker);
          StockStats stats = stock.getStats();
          StockDividend dividend = stock.getDividend();

          // stock.getHistory() TODO: this may be helpful in the future

          Morningstar.MorningstarStock morningstarStock = MorningstarQuotesRequest.getQuotes(ticker);
          MorningstarStockROI roi = MorningstarROIRequest.getQuotes(ticker);

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
              roi.getRoi10y());

          repository.save(newStock);
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


