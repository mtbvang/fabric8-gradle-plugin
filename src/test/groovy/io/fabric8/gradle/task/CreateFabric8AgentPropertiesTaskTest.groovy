package io.fabric8.gradle.task
import io.fabric8.gradle.BaseSpecification
import io.fabric8.gradle.plugin.Fabric8PluginExtension
import org.gradle.api.tasks.bundling.War
import org.gradle.testfixtures.ProjectBuilder
/**
 * @author sigge
 * @since 2014-06-16 16:25
 */
class CreateFabric8AgentPropertiesTaskTest extends BaseSpecification {

    def task

    def project

    def setup() {
        project = ProjectBuilder.builder().withName("fabric8-gradle-plugin").build()
        def createProfileTask = project.task('CreateProfileTask', type: CreateProfileTask) {
            project.ext.fabric8 = new Fabric8PluginExtension(profile: "my-test-profile")
        }

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
            project.file(project.buildDir.path + "/my/test/profile/io.fabric8.agent.properties").exists()

    }

    def "test determine packaging should be jar"() {
        expect:
            assert "jar" == task.determinePackaging(project)
    }

    def "test determine packaging should be war"() {
        given:
            project.task('war', type: War)
        expect:
            assert "war" == task.determinePackaging(project)
    }
}
