package com.p6spy.engine.spy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.spring.integration.test.annotation.SpringConfiguration;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import com.p6spy.engine.spy.impl.ContactService;
import com.p6spy.engine.spy.impl.CrossRepoService;
import com.p6spy.engine.spy.impl.CustommerService;

@RunWith(Arquillian.class)
@SpringConfiguration({ "ApplicationContext.xml", "ApplicationContext.XADataSource.xml" })
@Category( XADataSourceTests.class )
public class P6SpyXADataSourceTest extends P6SpyTestBase {

  @Autowired
  private CrossRepoService crossRepoService;

  @Autowired
  protected ContactService contactService;
  
  @Autowired
  private CustommerService custommerService;
  
  @Deployment
  public static WebArchive createDeployment() {
    return P6SpyTestBase
        .createDeployment()
        // test specific web(-<container>).xml descriptors
        .addAsWebInfResource(
            new FileAsset(
                new File(
                    // jboss is special
                    (isJBossOrWildFly() ? "src/test/config/web.XADataSource.jboss.xml"
                        : "src/test/config/web.XADataSource.xml"))), "web.xml")
        .addAsWebInfResource(new FileAsset(new File("src/test/config/glassfish-web.XADataSource.xml")), "glassfish-web.xml")

        // test specific spring context
        .addAsWebInfResource("ApplicationContext.XADataSource.xml",
            "classes/ApplicationContext.XADataSource.xml");
  }

  @Test
  public void shouldWorkCrossDSCommit() throws Exception {
    // do action
    crossRepoService.multiDSCommit(10, "name10");

    // check cross-DS commit worked
    assertThat(contactService.countById(10), equalTo(1));
    assertThat(custommerService.countById(10), equalTo(1));
    
    // make sure that the h2 DB file (proxied one) was really created
    assertThat(new File(getBuildDir() + "/h2/s1/test2.mv.db").exists(), equalTo(true));
    assertThat(new File(getBuildDir() + "/h2/s2/test1.mv.db").exists(), equalTo(true));
  }
  
  @Test
  public void shouldWorkCrossDSRollback() throws Exception {
    // do action
    try {
      crossRepoService.multiDSRollback(20, "name20");
    } catch (RuntimeException e) {
      
      // check cross-DS commit worked
      assertThat(contactService.countById(20), equalTo(0));
      assertThat(custommerService.countById(20), equalTo(0));
      
      // make sure that the h2 DB file (proxied one) was really created
      assertThat(new File(getBuildDir() + "/h2/s1/test2.mv.db").exists(), equalTo(true));
      assertThat(new File(getBuildDir() + "/h2/s2/test1.mv.db").exists(), equalTo(true));

      return;
    }
    
    // should never reach this point!
    Assert.fail();
  }
}
