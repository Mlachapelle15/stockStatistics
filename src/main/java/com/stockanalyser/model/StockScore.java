package com.stockanalyser.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import java.math.BigDecimal;
import java.util.Optional;

@Entity
public class StockScore {
  private static final BigDecimal MAX_PE = new BigDecimal(25);
  private static final BigDecimal PE_TARGET = new BigDecimal(17.5);
  private static final BigDecimal SIX = new BigDecimal(6);
  private static final BigDecimal TWELVE = new BigDecimal(12);
  private static final BigDecimal TWENTY = new BigDecimal(20);
  private static final BigDecimal TWO_HUNDREAD = new BigDecimal(200);
  private static final BigDecimal HUNDREAD = new BigDecimal(100);
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
    this.pe = Optional.ofNullable(stock.getPe()).map(pe -> pe.compareTo(MAX_PE) > 0).orElse(true)
        ? BigDecimal.ONE.negate()
        : computeScore(stock.getPe(), PE_TARGET, 2, true);
    this.payoutRatio = computeScore(stock.getPayoutRatio(), new BigDecimal(0), new BigDecimal(85), 3, false);
    this.annualYieldPercent = computeScore(stock.getAnnualYieldPercent(), new BigDecimal(3), new BigDecimal(6), 4, false);
    this.dividendGrowth5y = computeScore(stock.getDividendGrowth5y().multiply(HUNDREAD), TWENTY, new BigDecimal(10), 2, false);
    this.dividendGrowth10y = computeScore(stock.getDividendGrowth10y().multiply(HUNDREAD), TWENTY, new BigDecimal(10), 2, false);
    // TODO: 2017-03-16 the use of 2 param with .20 to 10 is a hack. The value does not drop fast enough with the other method.
    // TODO: 2017-03-16 Limit the result to be between maxScore and -maxScore
    this.roi1y = computeScore(stock.getRoi1y(), TWELVE, TWO_HUNDREAD, 1, false);
    this.roi5y = computeScore(stock.getRoi5y(), TWELVE, TWO_HUNDREAD, 1, false);
    this.roi10y = computeScore(stock.getRoi10y(), TWELVE, TWO_HUNDREAD, 1, false);
    //this.fcf = computeScore(stock.getFcf(), new BigDecimal(12), new BigDecimal(200), 1, false);
    this.fcfGrowth5y = computeScore(stock.getFcfGrowth5y().multiply(HUNDREAD), SIX, TWO_HUNDREAD, 1, false);
    this.fcfGrowth10y = computeScore(stock.getFcfGrowth10y().multiply(HUNDREAD), SIX, TWO_HUNDREAD, 1, false);
    this.epsGrowth5y = computeScore(stock.getEpsGrowth5y().multiply(HUNDREAD), SIX, TWO_HUNDREAD, 1, false);
    this.epsGrowth10y = computeScore(stock.getEpsGrowth10y().multiply(HUNDREAD), SIX, TWO_HUNDREAD, 1, false);
    this.score = getPe()
        .add(getPayoutRatio())
        .add(getAnnualYieldPercent())
        .add(getDividendGrowth5y())
        .add(getDividendGrowth10y())
        .add(getRoi1y())
        .add(getRoi5y())
        .add(getRoi10y())
        .add(getFcfGrowth5y())
        .add(getFcfGrowth10y())
        .add(getEpsGrowth5y())
        .add(getEpsGrowth10y());

    // TODO: 2017-03-16 manque la dette
    // TODO: 2017-03-18 we should stock eps,fcf... multipled by 100 instead og doing it here
  }

  // TODO: 2017-03-15 I should create a DividendStockScore and a GrowthStockScore...
  // todo(suite): ...because value or growth stock will have their score lowered with dividenv values

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
    int signum = value.signum();

    if ((lowerIsBest && value.compareTo(target) <= 0)
        || (!lowerIsBest && value.compareTo(target) >= 0)) {
      return maxScoreDecimal;
    }

    BigDecimal absValue = value.abs();

    BigDecimal percentDiffFromTarget = lowerIsBest
        ? target.divide(absValue, 4).multiply(maxScoreDecimal)
        : absValue.divide(target, 4).multiply(maxScoreDecimal);


    System.out.println("Percent diff from value(" + value + ") to target (" + target + ") %diff=" + percentDiffFromTarget);
    // note: if value is positive, we could return here and it
    if(signum >= 0){
      return capScore(percentDiffFromTarget, maxScoreDecimal);
    }else {
      // TODO: 2017-03-18 validate that the whle algo is corrrect
      return capScore(percentDiffFromTarget.negate(), maxScoreDecimal);
    }
  }

  private BigDecimal computeScore(BigDecimal value, BigDecimal targetMin, BigDecimal targetMax, int maxScore, boolean lowerIsBest) {
    BigDecimal maxScoreDecimal = new BigDecimal(maxScore);

    if ((value.compareTo(targetMin) >= 0) && (value.compareTo(targetMax) <= 0)) {
      return maxScoreDecimal;
    }

    return computeScore(value, (value.compareTo(targetMin) >= 0) ? targetMax : targetMin, maxScore, lowerIsBest);
  }

  private BigDecimal capScore(BigDecimal score, BigDecimal maxScore){
    BigDecimal result = score.min(maxScore).max(maxScore.negate());
    //System.out.println("Cap score. Score = " + score + " and maxScore = " + maxScore + " that give -> " + result);
    return result;
  }
}
