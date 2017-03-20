package com.stockanalyser.stock.morningstar;

import java.math.BigDecimal;

import com.stockanalyser.Utils;

public class MorningstarStockROI {
  private BigDecimal roi1y;
  private BigDecimal roi5y;

  public BigDecimal getRoi1y() {
    return roi1y;
  }

  public BigDecimal getRoi5y() {
    return roi5y;
  }

  public BigDecimal getRoi10y() {
    return roi10y;
  }

  private BigDecimal roi10y;

  public MorningstarStockROI() {

  }

  public MorningstarStockROI(String roi1y, String roi5y, String roi10y) {
    Utils.stringToBigDecimal(roi1y).ifPresent(value -> this.roi1y = value);
    Utils.stringToBigDecimal(roi5y).ifPresent(value -> this.roi5y = value);
    Utils.stringToBigDecimal(roi10y).ifPresent(value -> this.roi10y = value);
  }
}