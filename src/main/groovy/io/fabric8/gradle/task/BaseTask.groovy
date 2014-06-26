package io.fabric8.gradle.task

import io.fabric8.gradle.util.PluginUtil
import org.gradle.api.DefaultTask
/**
 * @author sigge
 * @since 2014-06-13 11:25
 */
abstract class BaseTask extends DefaultTask {
    def File destDir = project.buildDir

    def getProfileDir(project) {
        destDir.path + "/" + project.profileGenDir + "/" + PluginUtil.parseProfilenameIntoPath(project.fabric8.profile)
    }
}
