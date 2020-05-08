
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6

grails.plugin.location.base = "../base"

grails.project.dependency.resolution = {
  inherits("global") {}

  log "warn"
  checksums true

  repositories {
    inherits true

    grailsPlugins()
    grailsHome()
    grailsCentral()

    mavenLocal()
    // mavenCentral()
    mavenRepo "https://repo1.maven.org/maven2"

    mavenRepo "http://repo.grails.org/grails/plugins-releases/"
    mavenRepo "https://download.java.net/maven/2/"
    mavenRepo "https://repository.jboss.org/maven2/"
  }

  dependencies {
    compile "commons-collections:commons-collections:3.2.2"

    test 'mysql:mysql-connector-java:5.1.49'
    test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
  }

  plugins {
    build ":tomcat:7.0.54"

    compile ":federated-grails:0.6.0"
    compile ":build-test-data:2.2.3"
    compile ":mail:1.0"
    compile ":hibernate:3.6.10.16"

    test(":spock:0.7") {
      exclude "spock-grails-support"
    }
    test ":resources:1.1.6"
    provided ":greenmail:1.3.4"
  }
}
