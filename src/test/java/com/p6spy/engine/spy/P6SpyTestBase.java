package com.p6spy.engine.spy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

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
//    new File(System.getProperty("logDir"), "HERE_I_AM").createNewFile();
  }

  public static WebArchive createDeployment() {
    dumpProperties();
    
    return ShrinkWrap.create(EmbeddedGradleImporter.class)
        .forThisProjectDirectory().forTasks("build")//.withArguments("-x", "test", "-Pcontainer=jboss51x")
        .importBuildOutput().as(WebArchive.class)
        .addPackages(true, "com.p6spy.engine.spy")
        .addAsWebInfResource(new FileAsset(new File("src/test/config/glassfish-web.Driver.xml")), "glassfish-web.xml")
  
//        .addAsWebInfResource("ApplicationContext.xml");
        ;
    
//    return ShrinkWrap.create(WebArchive.class)
//        .addPackages(true, "com.p6spy.engine.spy")
////        .addAsResource("persistence.xml", "META-INF/persistence.xml")
//        .addAsWebInfResource(new FileAsset(new File("src/main/webapp/WEB-INF/web.xml")), "web.xml")
//        .addAsWebInfResource(new FileAsset(new File("src/main/webapp/WEB-INF/glassfish-web.xml")), "glassfish-web.xml")
//        .addAsWebInfResource("ApplicationContext.xml")
//        
//        .addAsWebInfResource(new FileAsset(new File("src/main/webapp/WEB-INF/glassfish-web.xml")), "glassfish-web.xml")
//        forProjectDirectory(".").-
////        .addAsLibraries(DependencyResolvers.use(MavenDependencyResolver.class).artifact("com.mydomain:itma-mybatis-impl-postgresql:1.0-SNAPSHOT").resolveAs(GenericArchive.class))
//        .addAsLibraries(DependencyResolver.use(EmbeddedGradleImporter.class).artifact("com.mydomain:itma-mybatis-impl-postgresql:1.0-SNAPSHOT").resolveAs(GenericArchive.class))
//        ;
    
//    return ShrinkWrap.create(WebArchive.class, "p6spy-it.war")
//        .addPackage(ContactRepository.class.getPackage())
//        // .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
//        .addAsResource("ApplicationContext.xml")
//        .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("web.xml"));
  }

  
  /**
   * Workaround for problem propagating properties to nested build via withArguments(), 
   * so using tmp file instead.
   * 
   * @see <a href="https://issues.jboss.org/browse/SHRINKWRAP-482">SHRINKWRAP-482</a>
   */
  private static void dumpProperties() {
    final File file = new File("build/nested_build.gradle.properties");
    file.deleteOnExit();
    
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(file);
      Properties properties = new Properties();
      properties.put("container", System.getProperty("arquillian.launch"));
      properties.store(fos, "");
    } catch (IOException e) {
      if (null != fos) {
        try {
          fos.close();
        } catch (IOException e1) {
        }
      }
      e.printStackTrace();
    }
  }
  
}
