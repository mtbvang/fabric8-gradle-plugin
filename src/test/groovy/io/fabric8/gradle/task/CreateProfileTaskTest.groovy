package io.fabric8.gradle.task
import io.fabric8.gradle.BaseSpecification
import io.fabric8.gradle.plugin.Fabric8PluginConvention
import io.fabric8.gradle.plugin.Fabric8PluginExtension
/**
 * @author sigge
 * @since 2014-06-11 20:17
 */
class CreateProfileTaskTest extends BaseSpecification {

    def task

    def setup() {
        task = project.task('CreateProfileTask', type: CreateProfileTask) {
            project.ext.fabric8 = new Fabric8PluginExtension(profile: "my-test-profile")
        }
        Fabric8PluginConvention convention = new Fabric8PluginConvention(project)
        project.convention.plugins.fabric8 = convention
    }

    def "createProfileDir"() {
        when:
            task.createProfileDirectories()
        then:
            assert task instanceof CreateProfileTask
            project.file(project.buildDir.path + "/generated/my/test/profile").exists()
    }
}
