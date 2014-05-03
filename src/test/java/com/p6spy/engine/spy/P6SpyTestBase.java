package com.p6spy.engine.spy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.gradle.archive.importer.embedded.EmbeddedGradleImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Value;

public abstract class P6SpyTestBase {

	public static final File SPY_LOG = new File(/*
												 * System.getProperty("buildDir")
												 * ,
												 */"spy.it.log");

	@BeforeClass
	public static void beforeClass() throws IOException {
		SPY_LOG.delete();
		assertThat(SPY_LOG.exists(), is(equalTo(false)));
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
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e1) {
				}
			}
			e.printStackTrace();
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
