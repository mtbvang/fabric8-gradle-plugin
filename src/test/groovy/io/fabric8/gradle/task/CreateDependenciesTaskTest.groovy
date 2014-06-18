package io.fabric8.gradle.task
import groovy.json.JsonSlurper
import io.fabric8.gradle.BaseSpecification
import io.fabric8.gradle.plugin.Fabric8PluginExtension
/**
 * @author sigge
 * @since 2014-06-17 13:40
 */
class CreateDependenciesTaskTest extends BaseSpecification {
    def configurations
    def task

    void setup() {
        project.ext {
            fabric8 = new Fabric8PluginExtension(profile: "my-test-profile", group: "io.fabric8", version: "1.0-SNAPSHOT")
        }
        configurations = project.configurations.create("compile")
        configurations = project.configurations.create("runtime")
        task = project.task('createDependenciesTask', type: CreateDependenciesTask)
        project.dependencies {
            delegate.compile("org.apache.logging.log4j:log4j:2.0-rc1")
            delegate.runtime("commons-http:commons-http:1.1")
        }
        if(!project.buildDir.exists()) {
            project.buildDir.mkdir()
        }
        task.dependenciesFile.createNewFile()
    }

    def "test createDependenciesFile"() {
        given:
         def dependenciesFile = project.file(project.buildDir.path + "/dependencies.json")
        when:
            task.buildDependenciesFile()
        then:
            assert dependenciesFile.exists()
            def result = new JsonSlurper().parseText(dependenciesFile.text)
            assert result.size() != 0

    }
}
