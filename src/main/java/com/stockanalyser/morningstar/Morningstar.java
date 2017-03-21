package com.stockanalyser.morningstar;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.stockanalyser.Utils;

/**
 * Created by mlachapelle on 2017-03-12.
 */
public class Morningstar {
  public static final Logger logger = Logger.getLogger(Morningstar.class.getName());

  public static class MorningstarStock {
    private BigDecimal dividend;
    private BigDecimal dividendGrowth5y;
    private BigDecimal dividendGrowth10y;
    private BigDecimal payoutRatio;
    private BigDecimal epsGrowth10y;
    private BigDecimal epsGrowth5y;
    private BigDecimal eps;
    private BigDecimal fcfGrowth10y;
    private BigDecimal fcfGrowth5y;
    private BigDecimal fcf;

    public MorningstarStock() {
    }

    public MorningstarStock(Map<String, List<String>> parsedCsv) {
      this();
      computeDividendStats(getParsedCsvFromRegex(parsedCsv, Pattern.compile("Dividends CAD", Pattern.CASE_INSENSITIVE)));
      computePayoutRatio(getParsedCsvFromRegex(parsedCsv, Pattern.compile("Payout Ratio", Pattern.CASE_INSENSITIVE)));
      computeEPSStats(getParsedCsvFromRegex(parsedCsv, Pattern.compile("Earnings Per Share CAD", Pattern.CASE_INSENSITIVE)));
      computeFCFStats(getParsedCsvFromRegex(parsedCsv, Pattern.compile("Free Cash Flow Per Share", Pattern.CASE_INSENSITIVE)));
    }

    private List<BigDecimal> getParsedCsvFromRegex(Map<String, List<String>> parsedCsv, Pattern regex) {
      Predicate<String> regexFilter = regex.asPredicate();
      List<String> values = parsedCsv
          .keySet()
          .stream()
          .filter(regexFilter)
          .findFirst()
          .map(parsedCsv::get)
          .orElse(Lists.newArrayList());

      return parseCsvRow(values);
    }

    // TODO: 2017-03-13 All the parsing should be in a class
    private List<BigDecimal> parseCsvRow(List<String> unparsedCsvRow) {
      return unparsedCsvRow.stream().map(Utils::stringToBigDecimal)
          .filter(Optional::isPresent)
          .map(Optional::get)
          .collect(Collectors.toList());
    }

    private void computePayoutRatio(List<BigDecimal> payoutRatios) {
      if (!payoutRatios.isEmpty()) {
        this.payoutRatio = payoutRatios.get(0);
      }
    }

    private void computeDividendStats(List<BigDecimal> dividends) {
      if (dividends.size() > 0) {
        dividend = dividends.get(0);
        compute5yAverage(dividends).ifPresent(divGrowth -> dividendGrowth5y = divGrowth);
        compute10yAverage(dividends).ifPresent(divGrowth -> dividendGrowth10y = divGrowth);
      }
    }

    private void computeEPSStats(List<BigDecimal> epsValues) {
      if (epsValues.size() > 0) {
        eps = epsValues.get(0);
        compute5yAverage(epsValues).ifPresent(divGrowth -> epsGrowth5y = divGrowth);
        compute10yAverage(epsValues).ifPresent(divGrowth -> epsGrowth10y = divGrowth);
      }
    }

    private void computeFCFStats(List<BigDecimal> fcfValues) {
      if (fcfValues.size() > 0) {
        fcf = fcfValues.get(0);
        compute5yAverage(fcfValues).ifPresent(divGrowth -> fcfGrowth5y = divGrowth);
        compute10yAverage(fcfValues).ifPresent(divGrowth -> fcfGrowth10y = divGrowth);
      }
    }

    private Optional<BigDecimal> compute5yAverage(List<BigDecimal> values) {
      // TODO: 2017-03-13 Validate that it do what I want
      return values.size() >= 5 ? Optional.of((values.get(0).min(values.get(5)).divide(BigDecimal.valueOf(5), 2))) : Optional.empty();
    }

    private Optional<BigDecimal> compute10yAverage(List<BigDecimal> values) {
      // TODO: 2017-03-13 Validate that it do what I want
      return values.size() >= 8 ? Optional.of((values.get(0).min(values.get(values.size() - 1)).divide(BigDecimal.valueOf(values.size()), 2))) : Optional.empty();
    }

    public BigDecimal getPayoutRatio() {
      return payoutRatio;
    }

    public BigDecimal getDividend() {
      return dividend;
    }

    public BigDecimal getDividendGrowth5y() {
      return dividendGrowth5y;
    }

    public BigDecimal getDividendGrowth10y() {
      return dividendGrowth10y;
    }

    public BigDecimal getEpsGrowth10y() {
      return epsGrowth10y;
    }

    public BigDecimal getEpsGrowth5y() {
      return epsGrowth5y;
    }

    public BigDecimal getEps() {
      return eps;
    }

    public BigDecimal getFcfGrowth10y() {
      return fcfGrowth10y;
    }

    public BigDecimal getFcfGrowth5y() {
      return fcfGrowth5y;
    }

    public BigDecimal getFcf() {
      return fcf;
    }
  }

}
