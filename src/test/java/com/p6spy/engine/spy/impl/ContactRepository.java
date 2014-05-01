package com.p6spy.engine.spy.impl;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

// initialized via spring configuration
//@Repository
public class ContactRepository {

  public static final String COUNT_ALL = "select count(*) from contacts";
  
  private JdbcTemplate jdbcTemplate;

//  @Autowired
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
    jdbcTemplate.execute("insert into contacts (id, name) values (" + id + ",'" + name + "')");
  }
}
