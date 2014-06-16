package io.fabric8.gradle.task

import io.fabric8.gradle.plugin.Fabric8PluginExtension
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author sigge
 * @since 2014-06-11 20:17
 */
class CreateProfileTaskTest extends Specification {

    def task

    @Shared
    def project

    def setup() {
        project = ProjectBuilder.builder().build()
        task = project.task('CreateProfileTask', type: CreateProfileTask) {
            project.ext.fabric8 = new Fabric8PluginExtension(profile: "my-test-profile")
        }
    }

    def "createProfileDir"() {
        when:
            task.createProfileDirectories()
        then:
            assert task instanceof CreateProfileTask
            project.file(project.buildDir.path + "/my/test/profile").exists()
    }
}
