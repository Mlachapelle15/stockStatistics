package com.stockanalyser.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockanalyser.stock.StockRepository;
import com.stockanalyser.stock.model.Stock;

@Service("stockService")
public class StockServiceImpl implements StockService {

  @Autowired
  private StockRepository stockRepository;

  @Override
  public List<Stock> getAllStocks() {
    return stockRepository.findAll();
  }
}