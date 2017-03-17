package com.stockanalyser;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by mlachapelle on 2017-03-14.
 */
public class Utils {
  public static Optional<BigDecimal> stringToBigDecimal(String value) {
    Optional<BigDecimal> parsedValue;
    try {
      parsedValue = Optional.of(new BigDecimal(value));
    } catch (NumberFormatException e) {
      parsedValue = Optional.empty();
    }
    return parsedValue;
  }
}
