package io.fabric8.gradle.task
import groovy.json.JsonSlurper
import io.fabric8.gradle.BaseSpecification
import io.fabric8.gradle.plugin.Fabric8PluginExtension
import org.gradle.api.plugins.WarPlugin
import spock.lang.Shared

/**
 * @author sigge
 * @since 2014-06-17 13:40
 */
class CreateDependenciesTaskTest extends BaseSpecification {
    static final String profilename = "my-test-profile"

    @Shared
    String profileDependenciesPath

    def configurations
    def task

    void setup() {
        project.ext {
            fabric8 = new Fabric8PluginExtension(profile: profilename, group: "io.fabric8", version: "1.0-SNAPSHOT", profileDir: project.file(project.buildDir.path + "/my/test/profile"))
        }
        profileDependenciesPath = project.fabric8.profileDir.path + "/dependencies/" + project.group + "/" + project.name + "-requirements.json"
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
        if(!project.fabric8.profileDir.exists()) {
            project.fabric8.profileDir.mkdirs()
        }
    }

    def "test createDependenciesFile for jar project - none should be created"() {
        given:
         def dependenciesFile = project.file(profileDependenciesPath)
        when:
            task.buildDependenciesFile()
        then:
            assert !dependenciesFile.exists()
    }

    def "test createDependenciesFile for war project"() {
        given:
            project.apply plugin: WarPlugin
            def dependenciesFile = project.file(profileDependenciesPath)
        when:
            task.buildDependenciesFile()
        then:
            assert dependenciesFile.exists()
            def result = new JsonSlurper().parseText(dependenciesFile.text)
            assert result.size() != 0
    }
}
