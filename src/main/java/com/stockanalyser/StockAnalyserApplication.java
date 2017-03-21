package com.stockanalyser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.stockanalyser")
@EntityScan(basePackages = {"com.stockanalyser"})
public class StockAnalyserApplication {
  private static final String DBNAME = "ticker";


  public static void main(String[] args) throws Exception {
    SpringApplication.run(StockAnalyserApplication.class, args);


/*
    // open the in-memory database within a VM

    Class.forName("org.h2.Driver"); // (1)
    Connection conn
        = DriverManager.getConnection("jdbc:h2:mem:" + DBNAME, "sa", "sa"); // (2)
    // username:password are very important and must be used for connecting via H2 Console

    Statement stat = conn.createStatement(); // (3)
    stat.executeUpdate("create table mytbl(ticker varchar(255) primary key )");
    stat.executeUpdate("insert into mytbl values('RY.TO')");
    //stat.executeUpdate("insert into mytbl values(2, 'World')");

    // Verify that sample data was really inserted
    ResultSet rs = stat.executeQuery("select * from mytbl");
    System.out.println("ResultSet output:");
    while (rs.next()) {
      System.out.println("> " + rs.getString("ticker"));
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
    System.out.println("Server is STOPPED");*/
  }

  /*@Bean(name = "dataSource")
  public DriverManagerDataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.h2.Driver");
    dataSource.setUrl("jdbc:h2:~/test;MV_STORE=false");
    dataSource.setUsername("sa");
    dataSource.setPassword("sa");

    // schema init
    Resource initSchema = new ClassPathResource("scripts/schema.sql");
    Resource initData = new ClassPathResource("scripts/data.sql");
    Resource selectData = new ClassPathResource("scripts/select-data.sql");
    DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, initData, selectData);
    DatabasePopulatorUtils.execute(databasePopulator, dataSource);

    return dataSource;
  }*/

  @Value("${score.target.div.growth}")
  private String[] test;

 /* @Bean
  //@DependsOn("dataSource")
  //public CommandLineRunner commandLineRunner(StockRepository repository) throws Exception {
  public ApplicationRunner applicationRunner(StockRepository repository) throws Exception {

    // open the in-memory database within a VM

    Class.forName("org.h2.Driver"); // (1)
    Connection conn
        = DriverManager.getConnection("jdbc:h2:mem:" + DBNAME, "sa", "sa"); // (2)
    // username:password are very important and must be used for connecting via H2 Console

    Statement stat = conn.createStatement(); // (3)
    //stat.executeUpdate("create table mytbl(ticker varchar(255) primary key )");
    //stat.executeUpdate("insert into mytbl values('RY.TO')");
    //stat.executeUpdate("insert into mytbl values(2, 'World')");

    // Verify that sample data was really inserted
    ResultSet rs = stat.executeQuery("select * from ticker");
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
*/
 /*@Bean
  public ApplicationRunner applicationRunner(StockRepository repository) throws Exception{
    return args -> {

      // TODO: 2017-03-19 Read this from DB
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
  }*/
}


