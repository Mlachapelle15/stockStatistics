package com.stockanalyser;

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
  public static void main(String[] args) throws Exception {
    SpringApplication.run(StockAnalyserApplication.class, args);
  }
}


