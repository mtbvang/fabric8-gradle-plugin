package io.fabric8.gradle.task
import io.fabric8.gradle.util.PluginUtil
import org.gradle.api.tasks.TaskAction
/**
 * @author sigge
 * @since 2014-06-11 20:16
 */
class CreateProfileTask extends BaseTask {

    @TaskAction
    def createProfileDirectories() {
        def fabric8 = project.fabric8
        def profilePath = destDir.path + "/" + PluginUtil.parseProfilenameIntoPath(fabric8.profile)
        // Setup profileDir in convention
        fabric8.profileDir = project.file(profilePath)
        logger.debug("Setting profileDir to $fabric8.profileDir")
        logger.info("Using profile directories: ${profilePath}")
        if(!project.file(profilePath).exists()) project.file(profilePath).mkdirs()
    }

}
