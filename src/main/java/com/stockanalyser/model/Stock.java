package com.stockanalyser.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Stock {
  @Id
  @Column(name = "ticker")
  private String ticker;

  @Column(name = "company_name")
  private String companyName;

  public Stock() {
    super();
  }

  public Stock(String ticker, String companyName) {
    this.ticker = ticker;
    this.companyName = companyName;
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }
}
