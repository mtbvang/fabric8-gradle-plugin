package io.fabric8.fabric8.gradle.task

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
        task = project.task('Fabric8GradleTask', type: CreateProfileTask) {
            profilename = "my-test-profile"
        }
    }

    def "createProfileDir"() {
        when:
            task.createProfileDir()
        then:
            assert task instanceof CreateProfileTask
            project.file("build/profile").exists()
    }
}
