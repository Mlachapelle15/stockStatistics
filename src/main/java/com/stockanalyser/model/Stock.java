package com.stockanalyser.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Stock {
  @Id
  @Column(name = "ticker")
  private String ticker;
  @Column(name = "company_name")
  private String companyName;
  @Column(name = "dividend_yield")
  private BigDecimal dividendYield;
  @Column(name = "pe")
  private  BigDecimal pe;
  @Column(name = "peg")
  private  BigDecimal peg;
  @Column(name = "annual_yield_percent")
  private  BigDecimal annualYieldPercent;
  @Column(name = "eps")
  private  BigDecimal eps;
  @Column(name = "roe")
  private  BigDecimal roe;
  @Column(name = "market_cap")
  private  BigDecimal marketCap;
  @Column(name = "one_year_target_price")
  private  BigDecimal oneYearTargetPrice;
  @Column(name = "ebitda")
  private  BigDecimal ebitda;
  @Column(name = "short_ratio")
  private  BigDecimal shortRatio;
  @Column(name = "book_value_per_share")
  private  BigDecimal bookValuePerShare;


  public Stock() {
    super();
  }

  public Stock(String ticker, String companyName){
    this.ticker = ticker;
    this.companyName = companyName;
  }

  public Stock(String ticker, String companyName, BigDecimal pe, BigDecimal peg, BigDecimal annualYieldPercent, BigDecimal eps, BigDecimal roe, BigDecimal marketCap, BigDecimal oneYearTargetPrice, BigDecimal ebitda, BigDecimal shortRatio, BigDecimal bookValuePerShare) {
    this.ticker = ticker;
    this.companyName = companyName;
    this.pe = pe;
    this.peg = peg;
    this.annualYieldPercent = annualYieldPercent;
    this.eps = eps;
    this.roe = roe;
    this.marketCap = marketCap;
    this.oneYearTargetPrice = oneYearTargetPrice;
    this.ebitda = ebitda;
    this.shortRatio = shortRatio;
    this.bookValuePerShare = bookValuePerShare;
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

  public BigDecimal getDividendYield() {
    return dividendYield;
  }

  public void setDividendYield(BigDecimal dividendYield) {
    this.dividendYield = dividendYield;
  }

  public BigDecimal getBookValuePerShare() {
    return bookValuePerShare;
  }

  public void setBookValuePerShare(BigDecimal bookValuePerShare) {
    this.bookValuePerShare = bookValuePerShare;
  }

  public BigDecimal getShortRatio() {
    return shortRatio;
  }

  public void setShortRatio(BigDecimal shortRatio) {
    this.shortRatio = shortRatio;
  }

  public BigDecimal getEbitda() {
    return ebitda;
  }

  public void setEbitda(BigDecimal ebitda) {
    this.ebitda = ebitda;
  }

  public BigDecimal getOneYearTargetPrice() {
    return oneYearTargetPrice;
  }

  public void setOneYearTargetPrice(BigDecimal oneYearTargetPrice) {
    this.oneYearTargetPrice = oneYearTargetPrice;
  }

  public BigDecimal getMarketCap() {
    return marketCap;
  }

  public void setMarketCap(BigDecimal marketCap) {
    this.marketCap = marketCap;
  }

  public BigDecimal getRoe() {
    return roe;
  }

  public void setRoe(BigDecimal roe) {
    this.roe = roe;
  }

  public BigDecimal getPe() {
    return pe;
  }

  public void setPe(BigDecimal pe) {
    this.pe = pe;
  }

  public BigDecimal getPeg() {
    return peg;
  }

  public void setPeg(BigDecimal peg) {
    this.peg = peg;
  }

  public BigDecimal getAnnualYieldPercent() {
    return annualYieldPercent;
  }

  public void setAnnualYieldPercent(BigDecimal annualYieldPercent) {
    this.annualYieldPercent = annualYieldPercent;
  }

  public BigDecimal getEps() {
    return eps;
  }

  public void setEps(BigDecimal eps) {
    this.eps = eps;
  }
}
