package com.stockanalyser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockanalyser.model.Stock;

@SpringBootApplication
public class StockAnalyserApplication {

  public static void main(String[] args) {
    SpringApplication.run(StockAnalyserApplication.class, args);
  }

 /* @Bean
  public CommandLineRunner commandLineRunner(StockRepository repository) {
    return args -> {
      Arrays.asList("TD.TO", "SJ.TO").forEach(ticker -> repository.save(new Stock(ticker)));

      repository.findAll().stream().forEach(t -> System.out.println(t));
    };
  }*/
}/*

@RestController
class StockRestController {
  @RequestMapping(path = "/stocks", method = RequestMethod.GET)
  Collection<Stock> stocks() {
    return this.stockRepository.findAll();
  }

  @Autowired
  StockRepository stockRepository;
}*/

// todo: this display the table but without data
/*@Controller
class StockMvcController {
  @RequestMapping("/stocks")
  String stock(Model model) {
    model.addAttribute("stocks", this.stockRepository.findAll());
    return "stocks"; //src/main/resources/templates/ + $x + .html
  }

  @Autowired
  StockRepository stockRepository;
}*/

@RestController
class StockMvcController {
  @RequestMapping("/stocks")
  public List<Stock> stocks() {
    return stockRepository.findAll();
  }

  @Autowired
  StockRepository stockRepository;
}


  /*@Component
  class StockResourceProcessor implements ResourceProcessor<Resource<Stock>>{

    @Override
    public Resource<Stock> process(Resource<Stock> stockResource) {
      return null;
    }
  }*/


