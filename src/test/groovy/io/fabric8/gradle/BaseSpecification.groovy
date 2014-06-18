package io.fabric8.gradle

import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification
/**
 * @author sigge
 * @since 2014-06-16 17:26
 */
class BaseSpecification extends Specification {
    def project

    void setup() {
        project = ProjectBuilder.builder().withName("fabric8-gradle-plugin").build()
    }

    def execute(task) {
        task.actions.each { action ->
            action.execute(task)
        }
    }
}
