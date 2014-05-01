p6spy-it
========

[![Build Status](https://secure.travis-ci.org/p6spy/p6spy-it.png?branch=master)](http://travis-ci.org/p6spy/p6spy-it)   

P6Spy Integration Testing

If you're looking for the configuration with the paticular app server, check the official docs: [Installation] (http://p6spy.github.io/p6spy/2.0/install.html)

Quickstart
----------

     git clone https://github.com/p6spy/p6spy-it.git
     cd p6spy-it
     ./gradlew clean unitTest -Pcontainer=<app_server>
     
where following are available `<app_server>`s provided:
  * `wildfly81x` - Wildfly 8.1.0.CR1
  * `jboss61x` - JBoss 6.1.0.Final
  * `jboss51x` - JBoss 5.1.0.GA
  * `jboss42x` - JBoss 4.2.3.GA
  * `glassfish4x` - Glassfish 4.0
  * `glassfish31x` - Glassfish 3.1.2.2

Misc
----

Idea is to setup container tests in a way that we can have full controll on what's happenning and the configuration steps should be documented per container in official docs: [Installation] (http://p6spy.github.io/p6spy/2.0/install.html)   