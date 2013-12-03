package com.p6spy.engine.spy;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

public class P6SpyLoggingIT {

  @Test
  public void logFileConstentsFit() throws IOException {
    final File spyLog = new File("target", "spy.log");
    
    Assert.assertTrue(spyLog.exists());
    Assert.assertTrue(FileUtils.readFileToString(spyLog, "UTF-8").contains("select count(*) from customers"));
  }
}
