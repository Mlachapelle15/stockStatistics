package com.stockanalyser;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ConfigurationProperties(prefix = "stock.score")
public class StockScoreConfig {
 /* private String peTest;
  private BigDecimal peTarget;

  StockScoreConfig() {
    this.peTarget = BigDecimal.ZERO;
  }

  public BigDecimal getPeTarget() {
    return this.peTarget;
  }


  public String getPeTest() {
    return peTest;
  }*/

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }
}
