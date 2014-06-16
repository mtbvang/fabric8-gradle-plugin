package io.fabric8.gradle.task

import io.fabric8.gradle.util.ProfileUtil
import org.gradle.api.tasks.TaskAction
/**
 * @author sigge
 * @since 2014-06-11 20:16
 */
class CreateProfileTask extends BaseTask {

    @TaskAction
    def createProfile() {
        def fabric8 = project.fabric8

        def profilePath = destDir.path + "/" + ProfileUtil.parseProfilenameIntoPath(fabric8.profile)
        logger.info("Creating profile directories: ${profilePath}")
        if(!project.file(profilePath).exists()) project.file(profilePath).mkdirs()
    }
}
