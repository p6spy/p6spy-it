package com.p6spy.engine.spy.impl;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//initialized via spring configuration
//@Repository
public class CustommerRepository {

  public static final String COUNT_ALL = "select count(*) from customers";
  
  private JdbcTemplate jdbcTemplate;
  
  public void setDataSource(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }
  
  public int countAll() {
    return jdbcTemplate.queryForObject(COUNT_ALL, Integer.class);  
  }
  
  public int countById(int id) {
    return jdbcTemplate.queryForObject(COUNT_ALL + " where id=" + id, Integer.class);  
  }
  
  public void insert(int id, String name) {
    jdbcTemplate.execute("insert into customers (id, name) values (" + id + ",'" + name + "')");
  }
}
