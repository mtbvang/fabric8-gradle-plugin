package io.fabric8.gradle.task
import io.fabric8.gradle.BaseSpecification
import io.fabric8.gradle.plugin.Fabric8PluginConvention
import io.fabric8.gradle.plugin.Fabric8PluginExtension
import io.fabric8.gradle.util.JolokiaClient
/**
 * @author sigge
 * @since 2014-06-30 10:41
 */
class DeployFabric8ProfileTaskTest extends BaseSpecification {

    def task
    def mockClient = Mock(JolokiaClient)
    def fabric8Settings

    def setup() {
        project.group = "io.fabric8"
        project.version = "1.0"
        fabric8Settings = new Fabric8PluginExtension(profile: "my-test-profile", parentProfiles: ["default", "tomcat"], version: "1.1", baseVersion: "1.0", bundles: ["bundle1", "bundle2"], featureRepositories: ["repo1",  "repo2"])
        task = project.task('deployFabric8Profile', type: DeployFabric8ProfileTask) {
            project.ext.fabric8 = fabric8Settings
            client = mockClient
            //setClient(mockClient)
        }
        Fabric8PluginConvention convention = new Fabric8PluginConvention(project)
        project.convention.plugins.put("fabric8", convention)
    }

    def "test profile deploy"() {
        when:
            task.deploy()
        then:
            1 * mockClient.execute(_, 'POST')
    }

    def "test build request"() {
        expect:
            task.buildRequirements(fabric8Settings).toString() == '{"type":"write","mbean":"io.fabric8:type=Fabric","profileId":"my-test-profile","version":"1.1","baseVersion":"1.0","parentProfiles":["default","tomcat"],"bundles":["bundle1","bundle2"],"featureRepositories":["repo1","repo2"],"rootDependency":"io.fabric8:fabric8-gradle-plugin:1.0"}'
    }
}
