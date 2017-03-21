package com.stockanalyser.stock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StockController {

  @RequestMapping(path="/", method=RequestMethod.GET)
	public String goHome(){
		return "index";
	}

}