package io.fabric8.gradle.plugin

import io.fabric8.gradle.BaseSpecification
import io.fabric8.gradle.task.CreateFabric8AgentPropertiesTask
import io.fabric8.gradle.task.CreateProfileTask
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Shared

/**
 * @author sigge
 * @since 2014-06-11 20:20
 */
class Fabric8PluginTest extends BaseSpecification {

    @Shared
    def project

    def setup() {
        project = ProjectBuilder.builder().withName("fabric8").build()
        project.apply plugin: 'fabric8'
    }

    def "test apply plugin"() {
        given:
            project.fabric8.profile = "my-test-profile"
            project.fabric8.group = "io.fabric8"
            project.fabric8.version = "1.0-SNAPSHOT"
            def configureTask = project.tasks.configure
            def createProfileTask = project.tasks.createProfile
            def createFabric8AgentPropertiesTask = project.tasks.createFabric8AgentProperties
        expect:
            assert configureTask instanceof Task
            assert createProfileTask instanceof CreateProfileTask
            assert createFabric8AgentPropertiesTask instanceof CreateFabric8AgentPropertiesTask

//            execute(configureTask)
//            execute(createProfileTask)

    }

}
