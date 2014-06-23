package io.fabric8.gradle.plugin
import io.fabric8.gradle.BaseSpecification
import io.fabric8.gradle.task.CreateFabric8AgentPropertiesTask
import io.fabric8.gradle.task.CreateProfileTask
import org.gradle.testfixtures.ProjectBuilder
/**
 * @author sigge
 * @since 2014-06-11 20:20
 */
class Fabric8PluginTest extends BaseSpecification {

    def project

    def setup() {
        project = ProjectBuilder.builder().withName("fabric8").build()
        project.apply plugin: Fabric8Plugin
    }

    def "test apply plugin"() {
        given:
            def createProfileTask = project.tasks.createProfile
            def createFabric8AgentPropertiesTask = project.tasks.createFabric8AgentProperties
        expect:
            assert createProfileTask instanceof CreateProfileTask
            assert createFabric8AgentPropertiesTask instanceof CreateFabric8AgentPropertiesTask

//            execute(configureTask)
//            execute(createProfileTask)

    }

}
