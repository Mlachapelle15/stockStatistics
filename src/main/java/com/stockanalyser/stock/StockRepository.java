package com.stockanalyser.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.stockanalyser.stock.model.Stock;

@RepositoryRestResource
public interface StockRepository extends JpaRepository<Stock, String> {

}