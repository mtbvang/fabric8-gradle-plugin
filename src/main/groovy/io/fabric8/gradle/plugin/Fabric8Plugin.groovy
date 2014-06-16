package io.fabric8.gradle.plugin

import io.fabric8.gradle.task.CreateProfileTask
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author sigge
 * @since 2014-06-11 19:42
 */
class Fabric8Plugin implements Plugin<Project>{


    void apply(Project target) {
        target.task('fabric8', type: CreateProfileTask)
    }

}
