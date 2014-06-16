package io.fabric8.gradle

import spock.lang.Specification

/**
 * @author sigge
 * @since 2014-06-16 17:26
 */
class BaseSpecification extends Specification {
    def execute(task) {
        task.actions.each { action ->
            action.execute(task)
        }
    }
}
