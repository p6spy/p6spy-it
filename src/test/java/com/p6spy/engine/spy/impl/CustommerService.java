package com.p6spy.engine.spy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustommerService {

  @Autowired
  private CustommerRepository repository;
  
  public int countAll() {
    return repository.countAll();
  }
  
  public int countById(int id) {
    return repository.countById(id);
  }
}
