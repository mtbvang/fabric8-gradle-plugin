package io.fabric8.gradle.task
import groovy.json.JsonSlurper
import io.fabric8.gradle.BaseSpecification
import io.fabric8.gradle.plugin.Fabric8PluginExtension
import org.gradle.testfixtures.ProjectBuilder
/**
 * @author sigge
 * @since 2014-06-17 13:40
 */
class CreateDependenciesTaskTest extends BaseSpecification {
    def project
    def configurations
    def task

    void setup() {
        project = ProjectBuilder.builder().withName("fabric8-gradle-plugin").build()
        project.ext {
            version = "1.0-SNAPSHOT"
            group = "io.fabric8"
            fabric8 = new Fabric8PluginExtension(profile: "my-test-profile")
        }
        configurations = project.configurations.create("compile")
        configurations = project.configurations.create("runtime")
        task = project.task('createDependenciesTask', type: CreateDependenciesTask)
        project.dependencies {
            delegate.compile("org.apache.logging.log4j:log4j:2.0-rc1")
            delegate.runtime("commons-http:commons-http:1.1")
        }
    }

    def "test createDependenciesFile"() {
        given:
         def dependenciesFile = project.file(project.buildDir.path + "/dependencies.json")
        when:
            task.createDependenciesFile()
        then:
            assert dependenciesFile.exists()
            def result = new JsonSlurper().parseText(dependenciesFile.text)
            assert result.size() != 0

    }
}
