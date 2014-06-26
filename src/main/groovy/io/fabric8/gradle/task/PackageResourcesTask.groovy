package io.fabric8.gradle.task

import org.gradle.api.tasks.TaskAction
/**
 * @author sigge
 * @since 2014-06-11 20:16
 */
class PackageResourcesTask extends BaseTask {

    @TaskAction
    def packageResources() {
        def fabric8 = project.fabric8
        def profilePath = getProfileDir(project)
        project.logger.info("Packaging resources into: ${profilePath}")
        project.copy {
            from project.fabric8.profileConfigDir
            into profilePath
        }
    }

}
