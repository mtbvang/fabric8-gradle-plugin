package io.fabric8.gradle.task
import io.fabric8.gradle.BaseSpecification
import io.fabric8.gradle.plugin.Fabric8PluginConvention
import io.fabric8.gradle.plugin.Fabric8PluginExtension
/**
 * @author sigge
 * @since 2014-06-11 20:17
 */
class PackageResourcesTaskTest extends BaseSpecification {

    def task

    def setup() {
        task = project.task('packageResources', type: PackageResourcesTask) {
            project.ext.fabric8 = new Fabric8PluginExtension(profile: "my-test-profile")
        }
        Fabric8PluginConvention convention = new Fabric8PluginConvention(project)
        project.convention.plugins.put("fabric8", convention)
        project.file(project.buildDir.path + "/generated/my/test/profile/").mkdirs()
        project.file("src/main/fabric8/").mkdirs()
        project.file("src/main/fabric8/test.properties").createNewFile()

    }

    def "packageResources"() {
        when:
            task.packageResources()
        then:
            assert task instanceof PackageResourcesTask
            project.file(project.buildDir.path + "/generated/my/test/profile/test.properties").exists()
    }
}
