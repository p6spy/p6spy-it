package com.p6spy.engine.spy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService implements TestService {

  @Autowired
  private ContactRepository repository;

  @Override
  public int countAll() {
    return repository.countAll();
  }
  
  @Override
  public int countById(int id) {
    return repository.countById(id);
  }
}
