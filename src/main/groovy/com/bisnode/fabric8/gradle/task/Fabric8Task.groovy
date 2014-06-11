package com.bisnode.fabric8.gradle.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author sigge
 * @since 2014-06-11 20:16
 */
class Fabric8Task extends DefaultTask {

    @TaskAction
    def createProfileDir() {
        def profileDir = "${project.projectDir}/src/main/fabric8"
        def destDir = project.buildDir

        project.file(destDir).mkdir()

    }
}
