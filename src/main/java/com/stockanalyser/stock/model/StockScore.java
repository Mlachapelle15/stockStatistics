package com.stockanalyser.stock.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import java.math.BigDecimal;

@Entity
public class StockScore {

  @Id
  @Column(name = "ticker")
  private String ticker;
  @Column(name = "company_name")
  private String companyName;
  @Column(name = "dividend_yield")
  private BigDecimal dividendYield;
  @Column(name = "pe")
  private BigDecimal pe;
  @Column(name = "peg")
  private BigDecimal peg;
  @Column(name = "annual_yield_percent")
  private BigDecimal annualYieldPercent;
  @Column(name = "eps")
  private BigDecimal eps;
  @Column(name = "roe")
  private BigDecimal roe;
  @Column(name = "market_cap")
  private BigDecimal marketCap;
  @Column(name = "one_year_target_price")
  private BigDecimal oneYearTargetPrice;
  @Column(name = "ebitda")
  private BigDecimal ebitda;
  @Column(name = "short_ratio")
  private BigDecimal shortRatio;
  @Column(name = "book_value_per_share")
  private BigDecimal bookValuePerShare;
  @Column
  private BigDecimal dividendGrowth5y;
  @Column
  private BigDecimal dividendGrowth10y;
  @Column
  private BigDecimal payoutRatio;
  @Column
  private BigDecimal morningstarStockEps;
  @Column
  private BigDecimal epsGrowth5y;
  @Column
  private BigDecimal epsGrowth10y;
  @Column
  private BigDecimal fcf;
  @Column
  private BigDecimal fcfGrowth5y;
  @Column
  private BigDecimal fcfGrowth10y;
  @Column
  private BigDecimal roi1y;
  @Column
  private BigDecimal roi5y;
  @Column
  private BigDecimal roi10y;
  @Column
  private BigDecimal score;

  public StockScore() {

  }

  public StockScore(String ticker, String companyName, BigDecimal dividendYield, BigDecimal pe, BigDecimal peg, BigDecimal annualYieldPercent, BigDecimal eps, BigDecimal roe, BigDecimal marketCap, BigDecimal oneYearTargetPrice, BigDecimal ebitda, BigDecimal shortRatio, BigDecimal bookValuePerShare, BigDecimal dividendGrowth5y, BigDecimal dividendGrowth10y, BigDecimal payoutRatio, BigDecimal morningstarStockEps, BigDecimal epsGrowth5y, BigDecimal epsGrowth10y, BigDecimal fcf, BigDecimal fcfGrowth5y, BigDecimal fcfGrowth10y, BigDecimal roi1y, BigDecimal roi5y, BigDecimal roi10y) {
    this.ticker = ticker;
    this.companyName = companyName;
    this.dividendYield = dividendYield;
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
    this.dividendGrowth5y = dividendGrowth5y;
    this.dividendGrowth10y = dividendGrowth10y;
    this.payoutRatio = payoutRatio;
    this.morningstarStockEps = morningstarStockEps;
    this.epsGrowth5y = epsGrowth5y;
    this.epsGrowth10y = epsGrowth10y;
    this.fcf = fcf;
    this.fcfGrowth5y = fcfGrowth5y;
    this.fcfGrowth10y = fcfGrowth10y;
    this.roi1y = roi1y;
    this.roi5y = roi5y;
    this.roi10y = roi10y;
    this.score = pe
        .add(payoutRatio)
        .add(annualYieldPercent)
        .add(dividendGrowth5y)
        .add(dividendGrowth10y)
        .add(roi1y)
        .add(roi5y)
        .add(roi10y)
        .add(fcfGrowth5y)
        .add(fcfGrowth10y)
        .add(epsGrowth5y)
        .add(epsGrowth10y);
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

  public BigDecimal getDividendGrowth5y() {
    return dividendGrowth5y;
  }

  public void setDividendGrowth5y(BigDecimal dividendGrowth5y) {
    this.dividendGrowth5y = dividendGrowth5y;
  }

  public BigDecimal getDividendGrowth10y() {
    return dividendGrowth10y;
  }

  public void setDividendGrowth10y(BigDecimal dividendGrowth10y) {
    this.dividendGrowth10y = dividendGrowth10y;
  }

  public BigDecimal getPayoutRatio() {
    return payoutRatio;
  }

  public void setPayoutRatio(BigDecimal payoutRatio) {
    this.payoutRatio = payoutRatio;
  }

  public BigDecimal getMorningstarStockEps() {
    return morningstarStockEps;
  }

  public void setMorningstarStockEps(BigDecimal morningstarStockEps) {
    this.morningstarStockEps = morningstarStockEps;
  }

  public BigDecimal getEpsGrowth5y() {
    return epsGrowth5y;
  }

  public void setEpsGrowth5y(BigDecimal epsGrowth5y) {
    this.epsGrowth5y = epsGrowth5y;
  }

  public BigDecimal getEpsGrowth10y() {
    return epsGrowth10y;
  }

  public void setEpsGrowth10y(BigDecimal epsGrowth10y) {
    this.epsGrowth10y = epsGrowth10y;
  }

  public BigDecimal getFcf() {
    return fcf;
  }

  public void setFcf(BigDecimal fcf) {
    this.fcf = fcf;
  }

  public BigDecimal getFcfGrowth5y() {
    return fcfGrowth5y;
  }

  public void setFcfGrowth5y(BigDecimal fcfGrowth5y) {
    this.fcfGrowth5y = fcfGrowth5y;
  }

  public BigDecimal getFcfGrowth10y() {
    return fcfGrowth10y;
  }

  public void setFcfGrowth10y(BigDecimal fcfGrowth10y) {
    this.fcfGrowth10y = fcfGrowth10y;
  }

  public BigDecimal getRoi1y() {
    return roi1y;
  }

  public void setRoi1y(BigDecimal roi1y) {
    this.roi1y = roi1y;
  }

  public BigDecimal getRoi5y() {
    return roi5y;
  }

  public void setRoi5y(BigDecimal roi5y) {
    this.roi5y = roi5y;
  }

  public BigDecimal getRoi10y() {
    return roi10y;
  }

  public void setRoi10y(BigDecimal roi10y) {
    this.roi10y = roi10y;
  }

  public BigDecimal getScore() {
    return score;
  }

  public void setScore(BigDecimal score) {
    this.score = score;
  }
}
