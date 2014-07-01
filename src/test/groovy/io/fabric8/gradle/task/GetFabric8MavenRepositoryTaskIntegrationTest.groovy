package io.fabric8.gradle.task

import io.fabric8.gradle.plugin.Fabric8PluginConvention
import io.fabric8.gradle.plugin.Fabric8PluginExtension
import io.fabric8.gradle.util.JolokiaClient
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * @author sigge
 * @since 2014-07-01 14:25
 */
class GetFabric8MavenRepositoryTaskIntegrationTest extends Specification {
    def project
    def task
    def fabric8Settings

    def setup() {
        project = ProjectBuilder.builder().withName("fabric8-gradle-plugin").build()
        fabric8Settings = new Fabric8PluginExtension(profile: "my-test-profile")
        task = project.task('getFabric8MavenRepositoryTask', type: GetFabric8MavenRepositoryTask) {
            project.ext.fabric8 = fabric8Settings
            client = new JolokiaClient("http://localhost:8181/jolokia", "admin", "admin")

        }
        Fabric8PluginConvention convention = new Fabric8PluginConvention(project)
        project.convention.plugins.put("fabric8", convention)
    }

    def "integration test profile deploy"() {
        when:
        def response = task.getMavenUploadUri()

        then:
            "http://${InetAddress.getLocalHost().getHostName()}:8181/maven/upload/" == response
    }

}
