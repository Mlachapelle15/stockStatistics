package com.stockanalyser.stock.service;

import java.util.List;

import com.stockanalyser.stock.model.Stock;

public interface StockService {
  public List<Stock> getAllStocks();
}