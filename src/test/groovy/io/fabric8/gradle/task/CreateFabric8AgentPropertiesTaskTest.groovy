package io.fabric8.gradle.task
import io.fabric8.gradle.BaseSpecification
import io.fabric8.gradle.plugin.Fabric8PluginConvention
import io.fabric8.gradle.plugin.Fabric8PluginExtension
/**
 * @author sigge
 * @since 2014-06-16 16:25
 */
class CreateFabric8AgentPropertiesTaskTest extends BaseSpecification {

    def task

    def setup() {
        def createProfileTask = project.task('CreateProfileTask', type: CreateProfileTask) {
            project.ext.fabric8 = new Fabric8PluginExtension(profile: "my-test-profile")
        }
        Fabric8PluginConvention convention = new Fabric8PluginConvention(project)
        project.convention.plugins.fabric8 = convention

        task = project.task('CreateFabric8AgentPropertiesTask', type: CreateFabric8AgentPropertiesTask) {
            project.fabric8 = new Fabric8PluginExtension(profile: "my-test-profile")
            project.group = "io.fabric8"
            project.version = "1.0-SNAPSHOT"
        }
        execute(createProfileTask)
    }

    def "test createProfileProperties"() {
        when:
            task.createFabric8AgentPropertiesFile()
        then:
            assert task instanceof CreateFabric8AgentPropertiesTask
            project.file(project.buildDir.path + "/generated/my/test/profile/io.fabric8.agent.properties").exists()

    }

}
