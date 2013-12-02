package com.p6spy.testwebapp;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

public class DbQuery {
  private DataSource dataSource;
  private JdbcTemplate jdbcTemplate;

  @PostConstruct
  public void init(){
    jdbcTemplate.queryForObject("select count(*) from customers", Integer.class);
  }

  @Resource(name="dataSource")
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
    jdbcTemplate = new JdbcTemplate(dataSource);
  }
}
