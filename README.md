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
  * `tomcat10x` - Tomcat 10.0.0   
  * `tomcat9x` - Tomcat 9.0.41
  * `tomcat8x` - Tomcat 8.5.61
  * `tomcat7x` - Tomcat 7.0.107
  * `tomcat6x` - Tomcat 6.0.53
  * `tomee7xweb` - Tomee 7.1.2 Webprofile
  * `tomee7xplus` - Tomee 7.1.2 Plus
  * `tomee7xplume` - Tomee 7.1.2 Plume
  * `tomee8xweb` - Tomee 8.0.4 Webprofile
  * `tomee8xplus` - Tomee 8.0.4 Plus
  * `tomee8xplume` - Tomee 8.0.4 Plume
  * `tomee8xmicro` - Tomee 8.0.4 Microprofile
  * `wls12x` - WebLogic 12.1.3 (for development)
  * `wlp16x` - Websphere Liberty Profile 16.0.0.2

However, due to travis-ci restriction on jdk availabiluty (jdk 7 not working), only following run in our CI env:
  * `wildfly81x`
  * `payara4x`
  * `glassfish4x`
  * `tomcat9x`
  * `tomcat8x`
  * `tomcat7x`
  * `tomcat6x`
  * `tomee7xweb`
  * `tomee7xplus`
  * `tomee7xplume`
  * `wls12x`
  * `wlp16x`

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
