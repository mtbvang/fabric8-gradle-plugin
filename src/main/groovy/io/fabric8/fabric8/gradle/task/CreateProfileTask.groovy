package io.fabric8.fabric8.gradle.task

import org.gradle.api.tasks.TaskAction
/**
 * @author sigge
 * @since 2014-06-11 20:16
 */
class CreateProfileTask extends BaseTask {
    def profileDir = "${project.projectDir}/src/main/fabric8"
    def destDir = project.buildDir

    @TaskAction
    def createProfileDir() {
        logger.info("Creating profile directories: ${profilename.split('-')}/")
        project.file(destDir).mkdir()
    }
}
