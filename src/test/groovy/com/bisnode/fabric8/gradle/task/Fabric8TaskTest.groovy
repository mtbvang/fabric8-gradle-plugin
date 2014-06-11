package com.bisnode.fabric8.gradle.task

import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author sigge
 * @since 2014-06-11 20:17
 */
class Fabric8TaskTest extends Specification {

    def task

    @Shared
    def project

    def setup() {
        project = ProjectBuilder.builder().build()
        task = project.task('Fabric8GradleTask', type: Fabric8Task)
        assert task instanceof Fabric8Task
    }

    def "createProfileDir"() {
        when:
            task.createProfileDir()
        then:
            project.file("build/profile").exists()
    }
}
