package io.fabric8.gradle.plugin
import io.fabric8.gradle.BaseSpecification
import io.fabric8.gradle.task.CreateFabric8AgentPropertiesTask
import io.fabric8.gradle.task.CreateProfileTask
import io.fabric8.gradle.task.PackageResourcesTask
import org.gradle.api.tasks.bundling.Zip
import org.gradle.testfixtures.ProjectBuilder
/**
 * @author sigge
 * @since 2014-06-11 20:20
 */
class Fabric8PluginTest extends BaseSpecification {

    def project

    def setup() {
        System.properties.put("fabric8.server.url", "http://localhost:8181")
        System.properties.put("fabric8.server.jolokia.path", "jolokia")
        System.properties.put("fabric8.server.user", "admin")
        System.properties.put("fabric8.server.password","admin")

        project = ProjectBuilder.builder().withName("fabric8").build()
        project.apply plugin: Fabric8Plugin
        project.fabric8 {
            profile = "my-test-profile"
        }
    }

    def "test apply plugin"() {
        given:
            project.fabric8.profile = "my-test-profile"
            def createProfileTask = project.tasks.createProfile
            def createFabric8AgentPropertiesTask = project.tasks.createFabric8AgentProperties
            def packageResourcesTask = project.tasks.packageResources
            def archiveFabric8ProfileTask = project.tasks.archiveFabric8Profile
        expect:
            assert createProfileTask instanceof CreateProfileTask
            assert createFabric8AgentPropertiesTask instanceof CreateFabric8AgentPropertiesTask
            assert packageResourcesTask instanceof PackageResourcesTask
            assert archiveFabric8ProfileTask instanceof Zip

//            execute(configureTask)
//            execute(createProfileTask)
              execute(packageResourcesTask)
              execute(archiveFabric8ProfileTask)
    }

}
