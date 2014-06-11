package com.bisnode.fabric8.gradle.plugin
import com.bisnode.fabric8.gradle.task.Fabric8Task
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification
/**
 * @author sigge
 * @since 2014-06-11 20:20
 */
class Fabric8PluginTest extends Specification {

    def project
    def plugin

    def setup() {
        project = ProjectBuilder.builder().build()
        project.apply plugin: 'fabric8'

    }

    def "Apply"() {
        expect:
        assert project.tasks.fabric8 instanceof Fabric8Task

    }
}
