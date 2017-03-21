package com.stockanalyser.stock;

import javax.sql.DataSource;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/*@Configuration
@EnableJpaRepositories(
    basePackages = "com.stockanalyser.ticker",
    entityManagerFactoryRef = "stockEntityManager",
    transactionManagerRef = "stockTransactionManager"
)*/
public class StockConfig {
  @Autowired
  private Environment env;

  @Bean
  public LocalContainerEntityManagerFactoryBean stockEntityManager() {
    LocalContainerEntityManagerFactoryBean em
        = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(stockDataSource());
    em.setPackagesToScan(
        new String[]{"com.stockanalyser.stock.model"});

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    HashMap<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto",
        env.getProperty("hibernate.hbm2ddl.auto"));
    properties.put("hibernate.dialect",
        env.getProperty("hibernate.dialect"));
    em.setJpaPropertyMap(properties);

    return em;
  }

  @Bean
  public DataSource stockDataSource() {

    DriverManagerDataSource dataSource
        = new DriverManagerDataSource();
    dataSource.setDriverClassName(
        "org.h2.Driver");
    dataSource.setUrl(env.getProperty("spring.datasource.url"));
    dataSource.setUsername(env.getProperty("spring.datasource.username"));
    dataSource.setPassword(env.getProperty("spring.datasource.password"));

    return dataSource;
  }

  @Bean
  public PlatformTransactionManager stockTransactionManager() {

    JpaTransactionManager transactionManager
        = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(
        stockEntityManager().getObject());
    return transactionManager;
  }
}