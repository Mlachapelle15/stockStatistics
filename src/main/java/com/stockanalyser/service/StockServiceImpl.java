package com.stockanalyser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockanalyser.StockRepository;
import com.stockanalyser.model.Stock;

@Service("employeeService")
public class StockServiceImpl implements StockService {

  @Autowired
  private StockRepository stockRepository;

  @Override
  public List<Stock> getAllStocks() {
    return stockRepository.findAll();
  }

  //@Override
  //public Employee getEmployeeById(long id) {
  //  return stockRepository.findOne(id);
  //}

}