package io.fabric8.gradle.task

import io.fabric8.gradle.util.ProfileUtil
import org.gradle.api.DefaultTask

/**
 * @author sigge
 * @since 2014-06-13 11:25
 */
class BaseTask extends DefaultTask {
    def destDir = project.buildDir

}
