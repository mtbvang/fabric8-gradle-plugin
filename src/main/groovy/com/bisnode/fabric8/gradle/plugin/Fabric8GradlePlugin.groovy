package com.bisnode.fabric8.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author sigge
 * @since 2014-06-11 19:42
 */
class Fabric8GradlePlugin implements Plugin<Project>{

    def profileDir = "${project.projectDir}/src/main/fabric8"
    def destDir = project.buildDir

    void apply(Project project) {
        project.task('hello') << {
            println profileDir
            //println project.file(profileDir)

        }
    }

}
