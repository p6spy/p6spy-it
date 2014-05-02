package com.p6spy.engine.spy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.io.IOException;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.gradle.archive.importer.embedded.EmbeddedGradleImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.BeforeClass;

public abstract class P6SpyTestBase {

  public static final File SPY_LOG = new File(/*System.getProperty("logDir"), */"spy.it.log");

  @BeforeClass
  public static void beforeClass() throws IOException {
    SPY_LOG.delete();
    assertThat(SPY_LOG.exists(), is(equalTo(false)));
  }

  public static WebArchive createDeployment() {
    return ShrinkWrap.create(EmbeddedGradleImporter.class)
        .forThisProjectDirectory()
        // rather using env variable: export ORG_GRADLE_PROJECT_container=wildfly81x ; ./gradlew clean unitTest
        //.forTasks("build")
        //.withArguments("-x", "test", "-Pcontainer=jboss51x")
        .importBuildOutput().as(WebArchive.class)
        .addPackages(true, "com.p6spy.engine.spy")
        .addAsWebInfResource(new FileAsset(new File("src/test/config/glassfish-web.Driver.xml")), "glassfish-web.xml")
        ;
  }
  
  protected static boolean isJBossOrWildFly() {
    return System.getProperty("arquillian.launch").contains("jboss") || System.getProperty("arquillian.launch").contains("wildfly");
  }
  
  protected static boolean isJBoss42x() {
    return System.getProperty("arquillian.launch").equals("jboss42x");
  }

}
