package io.fabric8.gradle.task
import io.fabric8.gradle.BaseSpecification
import io.fabric8.gradle.plugin.Fabric8PluginConvention
import io.fabric8.gradle.plugin.Fabric8PluginExtension
import io.fabric8.gradle.util.JolokiaClient
import org.jolokia.client.request.J4pReadRequest
import org.jolokia.client.request.J4pReadResponse
import org.json.simple.JSONObject

/**
 * @author sigge
 * @since 2014-07-01 14:25
 */
class GetFabric8MavenRepositoryTaskTest extends BaseSpecification {
    def task
    def mockClient = Mock(JolokiaClient)
    def fabric8Settings

    def setup() {
        fabric8Settings = new Fabric8PluginExtension(profile: "my-test-profile")
        task = project.task('getFabric8MavenRepositoryTask', type: GetFabric8MavenRepositoryTask) {
            project.ext.fabric8 = fabric8Settings
            client = mockClient
        }
        Fabric8PluginConvention convention = new Fabric8PluginConvention(project)
        project.convention.plugins.put("fabric8", convention)
    }

    def "test profile deploy"() {
        when:
            task.getMavenUploadUri()
        then:
            1 * mockClient.execute(_ as J4pReadRequest) >> new J4pReadResponse(new J4pReadRequest(""), new JSONObject(status: 200, value: "http://${InetAddress.getLocalHost().getHostName()}:8181/maven/upload/"))
    }

}
