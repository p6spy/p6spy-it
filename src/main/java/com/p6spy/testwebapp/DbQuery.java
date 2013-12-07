package com.p6spy.testwebapp;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class DbQuery {
//  private DataSource dataSource;
  private JdbcTemplate jdbcTemplate;

  @PostConstruct
  public void init(){
    jdbcTemplate.queryForObject("select count(*) from customers", Integer.class);
  }

  public void setDataSource(DataSource dataSource) {
//    this.dataSource = dataSource;
    jdbcTemplate = new JdbcTemplate(dataSource);
  }
}
