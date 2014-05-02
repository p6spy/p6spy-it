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
@SpringConfiguration({ "ApplicationContext.xml", "ApplicationContext.Driver.xml" })
@Category(DriverTests.class)
public class P6SpyDriverTest extends P6SpyTestBase {

  @Autowired
  protected ContactService contactService;

  @Deployment
  public static WebArchive createDeployment() {
    final WebArchive war = P6SpyTestBase
        .createDeployment()
        // test specific web(-<container>).xml descriptors

        .addAsWebInfResource(
            new FileAsset(
                new File(
                    // jboss is special
                    (isJBossOrWildFly() ? "src/test/config/web.Driver.jboss.xml"
                        : "src/test/config/web.Driver.xml"))), "web.xml")
        .addAsWebInfResource(new FileAsset(new File("src/test/config/glassfish-web.Driver.xml")),
            "glassfish-web.xml")

        // test specific spring context
        .addAsWebInfResource("ApplicationContext.Driver.xml",
            "classes/ApplicationContext.Driver.xml");

    // jboss42 is special
    if (isJBoss42x()) {
      war.addAsWebInfResource(new FileAsset(new File(
          "src/test/config/jboss-web.Driver.jboss42x.xml")), "jboss-web.xml");
    }

    return war;
  }

  @Test
  public void shouldWorkSpyLogFileAppending() throws Exception {
    // do action
    assertThat(contactService.countAll(), equalTo(2));

    // check logging worked
    assertThat(SPY_LOG.exists(), equalTo(true));
    assertThat(FileUtils.readFileToString(SPY_LOG, "UTF-8").contains(ContactRepository.COUNT_ALL),
        equalTo(true));
  }
}
