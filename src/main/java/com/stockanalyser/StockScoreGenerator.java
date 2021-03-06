package com.stockanalyser;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.stockanalyser.stock.model.GrowthStockScore;
import com.stockanalyser.stock.model.Stock;
import com.stockanalyser.stock.model.StockScore;

@Component
public class StockScoreGenerator {
  @Value("${stock.score.peTarget}")
  private BigDecimal targetPE;
  @Value("${stock.score.roiGrowthTarget}")
  private BigDecimal targetROIGrowth;
  @Value("${stock.score.fcfGrowthTarget}")
  private BigDecimal targetFCFGrowth;
  @Value("${stock.score.divGrowthTarget}")
  private BigDecimal targetDIVGrowth;
  @Value("${stock.score.epsGrowthTarget}")
  private BigDecimal targetEPSGrowth;
  @Value("${stock.score.revenueGrowthTarget}")
  private BigDecimal targetRevenueGrowth;


  private static final BigDecimal MAX_PE = new BigDecimal(25);
  private static final BigDecimal TWO_HUNDREAD = new BigDecimal(200);

  // TODO: 2017-03-28 Maybe I should compute the score thinking they other way. It's 0 until target and if you meet the target you start earning score.

  public StockScore create(Stock stock) {
    String ticker = stock.getTicker();
    String companyName = stock.getCompanyName();
    BigDecimal pe = Optional.ofNullable(stock.getPe()).map(value -> value.compareTo(MAX_PE) > 0).orElse(true)
        ? BigDecimal.ONE.negate()
        : computeScore(stock.getPe(), targetPE, 2, true);
    BigDecimal payoutRatio = computeScore(stock.getPayoutRatio(), new BigDecimal(0), new BigDecimal(85), 3, false);
    BigDecimal annualYieldPercent = computeScore(stock.getAnnualYieldPercent(), new BigDecimal(3), new BigDecimal(6), 4, false);
    BigDecimal dividendGrowth5y = computeScore(stock.getDividendGrowth5y(), targetDIVGrowth, new BigDecimal(10), 2, false);
    BigDecimal dividendGrowth10y = computeScore(stock.getDividendGrowth10y(), targetDIVGrowth, new BigDecimal(10), 2, false);
    // TODO: 2017-03-16 the use of 2 param with .20 to 10 is a hack. The value does not drop fast enough with the other method.
    // TODO: 2017-03-16 Limit the result to be between maxScore and -maxScore
    BigDecimal roi1y = computeScore(stock.getRoi1y(), targetROIGrowth, TWO_HUNDREAD, 1, false);
    BigDecimal roi5y = computeScore(stock.getRoi5y(), targetROIGrowth, TWO_HUNDREAD, 1, false);
    BigDecimal roi10y = computeScore(stock.getRoi10y(), targetROIGrowth, TWO_HUNDREAD, 1, false);
    //BigDecimal fcf = computeScore(stock.getFcf(), targetFCFGrowth, new BigDecimal(200), 1, false);
    BigDecimal fcfGrowth5y = computeScore(stock.getFcfGrowth5y(), targetFCFGrowth, TWO_HUNDREAD, 1, false);
    BigDecimal fcfGrowth10y = computeScore(stock.getFcfGrowth10y(), targetFCFGrowth, TWO_HUNDREAD, 1, false);
    BigDecimal epsGrowth5y = computeScore(stock.getEpsGrowth5y(), targetEPSGrowth, TWO_HUNDREAD, 1, false);
    BigDecimal epsGrowth10y = computeScore(stock.getEpsGrowth10y(), targetEPSGrowth, TWO_HUNDREAD, 1, false);
    BigDecimal revenueGrowth5y = computeScore(stock.getRevenueGrowth5y(), targetRevenueGrowth, TWO_HUNDREAD, 1, false);
    BigDecimal revenueGrowth10y = computeScore(stock.getRevenueGrowth10y(), targetRevenueGrowth, TWO_HUNDREAD, 1, false);

    // TODO: 2017-03-16 manque la dette

    return new StockScore(ticker, companyName, BigDecimal.ZERO, pe, BigDecimal.ZERO, annualYieldPercent, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, dividendGrowth5y, dividendGrowth10y, payoutRatio, BigDecimal.ZERO, epsGrowth5y, epsGrowth10y, BigDecimal.ZERO, fcfGrowth5y, fcfGrowth10y, roi1y, roi5y, roi10y, revenueGrowth5y, revenueGrowth10y);
  }

  public GrowthStockScore createGrowthScore(Stock stock) {
    String ticker = stock.getTicker();
    String companyName = stock.getCompanyName();
    BigDecimal pe = Optional.ofNullable(stock.getPe()).map(value -> value.compareTo(MAX_PE) > 0).orElse(true)
        ? BigDecimal.ONE.negate()
        : computeScore(stock.getPe(), targetPE, 2, true);
    // TODO: 2017-03-16 the use of 2 param with .20 to 10 is a hack. The value does not drop fast enough with the other method.
    // TODO: 2017-03-16 Limit the result to be between maxScore and -maxScore
    BigDecimal roi1y = computeScore(stock.getRoi1y(), targetROIGrowth, TWO_HUNDREAD, 1, false);
    BigDecimal roi5y = computeScore(stock.getRoi5y(), targetROIGrowth, TWO_HUNDREAD, 1, false);
    BigDecimal roi10y = computeScore(stock.getRoi10y(), targetROIGrowth, TWO_HUNDREAD, 1, false);
    BigDecimal fcfGrowth5y = computeScore(stock.getFcfGrowth5y(), targetFCFGrowth, TWO_HUNDREAD, 1, false);
    BigDecimal fcfGrowth10y = computeScore(stock.getFcfGrowth10y(), targetFCFGrowth, TWO_HUNDREAD, 1, false);
    BigDecimal epsGrowth5y = computeScore(stock.getEpsGrowth5y(), targetEPSGrowth, TWO_HUNDREAD, 1, false);
    BigDecimal epsGrowth10y = computeScore(stock.getEpsGrowth10y(), targetEPSGrowth, TWO_HUNDREAD, 1, false);
    BigDecimal revenueGrowth5y = computeScore(stock.getRevenueGrowth5y(), targetRevenueGrowth, TWO_HUNDREAD, 1, false);
    BigDecimal revenueGrowth10y = computeScore(stock.getRevenueGrowth10y(), targetRevenueGrowth, TWO_HUNDREAD, 1, false);
    // TODO: 2017-03-16 manque la dette

    return new GrowthStockScore(ticker, companyName, pe, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, epsGrowth5y, epsGrowth10y, fcfGrowth5y, fcfGrowth10y, roi1y, roi5y, roi10y, revenueGrowth5y, revenueGrowth10y);
  }

  private BigDecimal computeScore(BigDecimal value, BigDecimal target, int maxScore, boolean lowerIsBest) {
    if (value == null) {
      return BigDecimal.ZERO;
    }

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
    if (signum >= 0) {
      return capScore(percentDiffFromTarget, maxScoreDecimal);
    } else {
      // TODO: 2017-03-18 validate that the whle algo is corrrect
      return capScore(percentDiffFromTarget.negate(), maxScoreDecimal);
    }
  }

  private BigDecimal computeScore(BigDecimal value, BigDecimal targetMin, BigDecimal targetMax, int maxScore, boolean lowerIsBest) {
    if (value == null) {
      return BigDecimal.ZERO;
    }

    BigDecimal maxScoreDecimal = new BigDecimal(maxScore);

    if ((value.compareTo(targetMin) >= 0) && (value.compareTo(targetMax) <= 0)) {
      return maxScoreDecimal;
    }

    return computeScore(value, (value.compareTo(targetMin) >= 0) ? targetMax : targetMin, maxScore, lowerIsBest);
  }

  private BigDecimal capScore(BigDecimal score, BigDecimal maxScore) {
    BigDecimal result = score.min(maxScore).max(maxScore.negate());
    return result;
  }
}
