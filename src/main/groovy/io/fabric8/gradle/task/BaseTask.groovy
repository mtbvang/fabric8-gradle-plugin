package io.fabric8.gradle.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.OutputDirectory

/**
 * @author sigge
 * @since 2014-06-13 11:25
 */
abstract class BaseTask extends DefaultTask {
    @OutputDirectory
    def File destDir = project.buildDir


}
