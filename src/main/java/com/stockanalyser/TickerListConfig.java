package com.stockanalyser.ticker;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "tickers")
public class TickerListConfig {
  private List<String> list;

  TickerListConfig() {
    this.list = new ArrayList<>();
  }

  public List<String> getList() {
    return this.list;
  }
}
