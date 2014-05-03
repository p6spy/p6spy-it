package com.p6spy.engine.spy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.spring.integration.test.annotation.SpringConfiguration;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import com.p6spy.engine.spy.impl.ContactRepository;
import com.p6spy.engine.spy.impl.ContactService;

@RunWith(Arquillian.class)
@SpringConfiguration({ "ApplicationContext.xml", "ApplicationContext.DataSource.xml" })
@Category( DataSourceTests.class )
public class P6SpyDataSourceTests extends P6SpyTestBase {

  @Deployment
  public static WebArchive createDeployment() {
    return P6SpyTestBase
        .createDeployment()
        // test specific web(-<container>).xml descriptors
        .addAsWebInfResource(new FileAsset(new File("src/test/config/web.DataSource.xml")),
            "web.xml")
        .addAsWebInfResource(new FileAsset(new File("src/test/config/glassfish-web.DataSource.xml")), "glassfish-web.xml")
//        .addAsWebInfResource(new FileAsset(new File("src/test/config/jboss-web.DataSource.xml")), "jboss-web.xml")
        
        // test specific spring context
        .addAsWebInfResource("ApplicationContext.DataSource.xml",
            "classes/ApplicationContext.DataSource.xml");
  }

  @Autowired
  protected ContactService contactService;
  
  @Test
  public void shouldWorkSpyLogFileAppending() throws Exception {
    // do action
    assertThat(contactService.countAll(), equalTo(2));

    // check logging worked
    assertThat(SPY_LOG.exists(), equalTo(true));
    assertThat(FileUtils.readFileToString(SPY_LOG, "UTF-8").contains(ContactRepository.COUNT_ALL),
        equalTo(true));
    
    // make sure that the h2 DB file (proxied one) was really created
    assertThat(new File(getBuildDir() + "/h2/s1/test1.mv.db").exists(), equalTo(true));
  }
}
