#!/usr/bin/env groovy

// load existing settings.xml file
def originalSettingsFile = new File(System.getProperty("user.home"), ".m2/settings.xml")
if( !originalSettingsFile.exists() ) {
  println "Could not load settings from ${originalSettingsFile.absolutePath}"
  System.exit(-1)
}
def settings = new XmlParser().parse(originalSettingsFile);
println "Maven settings loaded from ${originalSettingsFile.absolutePath}"

// add cloudbees repositories
def profiles = settings.profiles
if( profiles.size() == 0 ) {
  // create the node if it did not exist
  settings.append(new NodeBuilder().createNode("profiles"))
  profiles = settings.profiles
}

println "Appending profile for cloudbees repositories"
profiles[0].append(NodeBuilder.newInstance().profile {
  id('cloudbees')
  repositories {
    repository {
      id('cloudbees-release')
      snapshots {
        enabled('false')
      }
      name('cloudbees-release')
      url('http://repository-p6spy.forge.cloudbees.com/release')
    }
  }
})

println "Appending profile for codehaus snapshots repository"
profiles[0].append(NodeBuilder.newInstance().profile {
  id('codehaus-snapshots')
  repositories {
    repository {
      id('codehaus-snapshots')
      releases {
        enabled('false')
      }
      snapshots {
        enabled('true')
      }
      name('codehaus-snapshots')
      url('http://nexus.codehaus.org/snapshots/')
    }
  }
  pluginRepositories {
    pluginRepository {
      id('codehaus-snapshots')
      releases {
        enabled('false')
      }
      snapshots {
        enabled('true')
      }
      name('codehaus-snapshots')
      url('http://nexus.codehaus.org/snapshots/')
    }
  }
})

def activeProfiles = settings.activeProfiles
if( activeProfiles.size() == 0 ) {
  // create the node if it did not exist
  settings.append(new NodeBuilder().createNode("activeProfiles"))
  activeProfiles = settings.activeProfiles
}

activeProfiles[0].append(NodeBuilder.newInstance().activeProfile('cloudbees'))
activeProfiles[0].append(NodeBuilder.newInstance().activeProfile('codehaus-snapshots'))

// write out new settings.xml file
def targetFile = new File(originalSettingsFile.parentFile, 'itSettings.xml')
println "Writing ${targetFile.absolutePath}"
def writer = new FileWriter(targetFile)
new XmlNodePrinter(new PrintWriter(writer)).print(settings)
writer.close()
