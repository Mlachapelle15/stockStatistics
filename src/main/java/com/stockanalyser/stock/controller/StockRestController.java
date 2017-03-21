package com.stockanalyser.stock.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stockanalyser.stock.model.Stock;
import com.stockanalyser.stock.model.StockScore;
import com.stockanalyser.stock.service.StockService;

@RestController
public class StockRestController {

  @Autowired
  private StockService stockService;

  @RequestMapping(path = "/stocks", method = RequestMethod.GET)
  public List<Stock> getAllStocks() {
    return stockService.getAllStocks();
  }

  @RequestMapping(path = "/stockScores", method = RequestMethod.GET)
  public List<StockScore> getAllStockScores() {
    return stockService.getAllStocks().stream().map(StockScore::new).collect(Collectors.toList());
  }

  // @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
  //public Stock getEmployeeById(@PathVariable("id") long id) {
  // return stockService.getEmployeeById(id);
  //}

}