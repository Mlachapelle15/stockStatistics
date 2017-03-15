package com.stockanalyser.model;

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

  public StockScore(Stock stock) {
    this.ticker = stock.getTicker();
    this.companyName = stock.getCompanyName();
    this.pe = computeScore(stock.getPe(), new BigDecimal(16.5), 2, true);
    this.payoutRatio = computeScore(stock.getPayoutRatio(), new BigDecimal(90), 3, true);
    this.annualYieldPercent = computeScore(stock.getAnnualYieldPercent(), new BigDecimal(3), new BigDecimal(6), 4, false);
    this.dividendGrowth5y = computeScore(stock.getDividendGrowth5y(), new BigDecimal(0.20), 2, false);
    this.dividendGrowth10y = computeScore(stock.getDividendGrowth10y(), new BigDecimal(0.20), 2, false);
    this.score = getPe()
        .add(getPayoutRatio())
        .add(getAnnualYieldPercent())
        .add(getDividendGrowth5y())
        .add(getDividendGrowth10y());
  }

  // TODO: 2017-03-15 I should create a DividendStockScore and a GrowthStockScore...

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

  private BigDecimal computeScore(BigDecimal value, BigDecimal target, int maxScore, boolean lowerIsBest) {
    BigDecimal maxScoreDecimal = new BigDecimal(maxScore);

    if ((lowerIsBest && value.compareTo(target) <= 0)
        || (!lowerIsBest && value.compareTo(target) >= 0)) {
      return maxScoreDecimal;
    }


    BigDecimal percentDiffFromTarget = lowerIsBest ?
        target.divide(value, 4).multiply(maxScoreDecimal)
        : value.divide(target, 4).multiply(maxScoreDecimal);

    System.out.println("Percent diff from target " + percentDiffFromTarget);

    return percentDiffFromTarget;
  }

  private BigDecimal computeScore(BigDecimal value, BigDecimal targetMin, BigDecimal targetMax, int maxScore, boolean lowerIsBest) {
    BigDecimal maxScoreDecimal = new BigDecimal(maxScore);

    if (value.compareTo(targetMin) >= 0 && value.compareTo(targetMax) <= 0) {
      return maxScoreDecimal;
    }

    BigDecimal percentDiffFromTarget =
        computeScore(value, (value.compareTo(targetMin) >= 0) ? targetMax : targetMin, maxScore, lowerIsBest);

    return percentDiffFromTarget;
  }

  public BigDecimal computeAndSetScore() {
    return BigDecimal.ZERO;
/*
    if (stockObject.pe > 25) {
      score -= 2;
    }

    if (stockObject.yield < 6 && stockObject.yield > 2) {
      score += 2;
    }

    if (stockObject.divGrowth > 10) {
      score++;
    }

    if (stockObject.divGrowth10 > 10) {
      score++;
    }

    if (stockObject.payoutRatio > 100) {
      score += 2;
    }

    if (stockObject.payoutRatio < 80 && stockObject.payoutRatio > 30) {
      score += 2;
    }

    if (stockObject.eps1 > 6) {
      score++;
    }

    if (stockObject.eps5 > 6) {
      score++;
    }

    if (stockObject.eps10 > 6) {
      score++;
    }

    if (stockObject.fcfGrowth1 > 6) {
      score++;
    }

    if (stockObject.fcfGrowth5 > 6) {
      score++;
    }

    if (stockObject.fcfGrowth10 > 6) {
      score++;
    }

    if (stockObject.roi1 > 10) {
      score++;
    }

    if (stockObject.roi5 > 12) {
      score++;
    }

    if (stockObject.roi10 > 12) {
      score++;
    }
    //todo mlachapelle: il manque la dette
    stockObject.score = score;*/
  }

}
