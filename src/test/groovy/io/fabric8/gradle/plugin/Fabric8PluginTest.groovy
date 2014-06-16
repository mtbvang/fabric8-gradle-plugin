package io.fabric8.gradle.plugin

import io.fabric8.gradle.task.CreateProfileTask
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification
/**
 * @author sigge
 * @since 2014-06-11 20:20
 */
class Fabric8PluginTest extends Specification {

    def project

    def setup() {
        project = ProjectBuilder.builder().build()
        project.apply plugin: 'fabric8'
    }

    def "test apply plugin"() {
        given:
            project.fabric8.profile = "my-test-profile"
            def configureTask = project.tasks.configure
            def createProfileTask = project.tasks.createProfile
        expect:
            assert configureTask instanceof Task
            assert createProfileTask instanceof CreateProfileTask

            execute(configureTask)
            execute(createProfileTask)

    }

    def execute(task) {
        task.actions.each { action ->
            action.execute(task)
        }
    }
}
