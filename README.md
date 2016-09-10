p6spy-it
========

[![Build Status](https://secure.travis-ci.org/p6spy/p6spy-it.png?branch=master)](http://travis-ci.org/p6spy/p6spy-it)   

P6Spy Integration Testing

If you're looking for the configuration with the particular app server, check the official docs: [Installation] (http://p6spy.github.io/p6spy/2.0/install.html)

Quickstart
----------

     git clone https://github.com/p6spy/p6spy-it.git
     cd p6spy-it
     export ORG_GRADLE_PROJECT_container=<app_server>; ./gradlew clean unitTest
     
where following are available `<app_server>`s provided:
  * `wildfly81x` - Wildfly 8.1.0.Final
  * `jboss61x` - JBoss 6.1.0.Final
  * `jboss51x` - JBoss 5.1.0.GA
  * `jboss42x` - JBoss 4.2.3.GA
  * `payara4x` - Payara 4.1.144
  * `glassfish4x` - Glassfish 4.0
  * `glassfish31x` - Glassfish 3.1.2.2
  * `tomcat9x` - Tomcat 9.0.0 M10
  * `tomcat8x` - Tomcat 8.0.15
  * `tomcat7x` - Tomcat 7.0.54
  * `tomcat6x` - Tomcat 6.0.39
  * `tomee16xweb` - Tomee 1.6.0.2 Webprofile
  * `tomee16xplus` - Tomee 1.6.0.2 Plus
  * `wls12x` - WebLogic 12.1.3 (for development)

Debugging
---------

To debug JUnit test (**not in-container part**) run, use:

	export ORG_GRADLE_PROJECT_container=<app_server>; ./gradlew clean unitTest -Dtest.debug=true

and connect via the IDE of your choice for remote debugging afterwards using the port `5005`.


To debug JUnit test (**in-container part**) run adopt `arquilian.xml` respectively afterwards use:

	export ORG_GRADLE_PROJECT_container=<app_server>; ./gradlew clean unitTest
	
and connect via the IDE of your choice for remote debugging afterwards using the port specified.

Misc
----

Idea is to setup container tests in a way that we can have full control on what's happening and the configuration steps should be documented per container in official docs: [Installation] (http://p6spy.github.io/p6spy/2.0/install.html)   
