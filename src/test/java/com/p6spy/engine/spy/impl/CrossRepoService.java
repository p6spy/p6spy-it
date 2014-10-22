package com.p6spy.engine.spy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CrossRepoService {

  @Autowired
  private ContactRepository contactRepository;
  
  @Autowired
  private CustommerRepository custommerRepository;
  
  @Transactional
  public void multiDSCommit(int id, String name) {
    custommerRepository.insert(id, name);
    contactRepository.insert(id, name);
  }
  
  @Transactional(rollbackFor = { RuntimeException.class })
  public void multiDSRollback(int id, String name) {
    custommerRepository.insert(id, name);
    contactRepository.insert(id, name);

    // go for rollback
    throw new RuntimeException();
  }
}
