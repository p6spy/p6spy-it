package com.p6spy.engine.spy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.gradle.archive.importer.embedded.EmbeddedGradleImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;


public abstract class P6SpyTestBase {

	protected static final File SPY_LOG = new File(/*
												 * System.getProperty("buildDir")
												 * getBuildDir()
												 * ,
												 */
	                        "spy.it.log");

	@Before
	public void beforeClass() throws IOException {
	  // for some reason tomee loads this class twice during single test => can't simply 
	  // delete, rather clear file contents
//	  SPY_LOG.delete();
//		assertThat(SPY_LOG.exists(), equalTo(false));
	  
	  RandomAccessFile raf = null;
	  try {
	    raf = new RandomAccessFile(SPY_LOG, "rw");
	    raf.setLength(0);
	  } finally {
	    if (null != raf) {
	      raf.close();
	    }
	  }
	}

	public static WebArchive createDeployment() {
		return ShrinkWrap
				.create(EmbeddedGradleImporter.class)
				.forThisProjectDirectory()
				// rather using env variable: export
				// ORG_GRADLE_PROJECT_container=wildfly81x ; ./gradlew clean
				// unitTest
				// .forTasks("build")
				// .withArguments("-x", "test", "-Pcontainer=jboss51x")
				.importBuildOutput()
				.as(WebArchive.class)
				.addPackages(true, "com.p6spy.engine.spy")
				.addAsWebInfResource(
						new FileAsset(new File(
								"src/test/config/glassfish-web.Driver.xml")),
						"glassfish-web.xml")

				// propagate system properties to in-container junit tests
				.addAsResource(getFileDumpedSystemProperties());
	}

	/**
	 * @return (temporary) properties file holding dumped system properties.
	 */
	private static File getFileDumpedSystemProperties() {
		final File file = new File("build/system.properties");
		file.deleteOnExit();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			System.getProperties().store(fos, "");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		  if (null != fos) {
        try {
          fos.close();
        } catch (IOException e1) {
        }
      }
		}
		return file;
	}

	protected static boolean isJBossOrWildFly() {
		return System.getProperty("arquillian.launch").contains("jboss")
				|| System.getProperty("arquillian.launch").contains("wildfly");
	}

	protected static boolean isJBoss42x() {
		return System.getProperty("arquillian.launch").equals("jboss42x");
	}

	// injected in the container
	@Value("${buildDir}")
  private String buildDir;
	
	protected String getBuildDir() {
		return buildDir;
	}
}
