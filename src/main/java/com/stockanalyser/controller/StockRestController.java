package com.stockanalyser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stockanalyser.model.Stock;
import com.stockanalyser.service.StockService;

@RestController
public class StockRestController {

  @Autowired
  private StockService stockService;

  @RequestMapping(path = "/employees", method = RequestMethod.GET)
  public List<Stock> getAllStocks() {
    return stockService.getAllStocks();
  }

  // @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
  //public Stock getEmployeeById(@PathVariable("id") long id) {
  // return stockService.getEmployeeById(id);
  //}

}