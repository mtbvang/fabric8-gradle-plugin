package io.fabric8.gradle.task

import groovy.json.JsonBuilder
import io.fabric8.gradle.util.PluginUtil
import org.gradle.api.tasks.TaskAction
/**
 * @author sigge
 * @since 2014-06-11 20:16
 */
class CreateDependenciesTask extends BaseTask {

    @TaskAction
    def createDependenciesFile() {
        logger.info("Creating profile dependencies")
        def fabric8 = project.fabric8

        if(!project.file(project.buildDir.path).exists()) {
            project.file(project.buildDir.path).mkdir()
        }
        def dependenciesFile = project.file(project.buildDir.path + "/dependencies.json")
        dependenciesFile.createNewFile()
        def dependencies = [] as List
        // Loop through all dependencies and write to file
        project.configurations.all { configuration ->
            configuration.dependencies.each { dependency ->
                dependencies << [
                        groupId: dependency.group,
                        artifactId: dependency.name,
                        //classifier: dependency.classifier,
                        scope:configuration.name,
                        version:dependency.version,
                        //optional: dependency.optional
                ]
            }
        }

        def builder = new JsonBuilder()
        builder {
            profileId fabric8.profile
            parentProfiles fabric8.parentProfile
            rootDependency {
                groupId project.ext.group
                artifactId project.name
                version project.ext.version
                type PluginUtil.determinePackaging(project)
                optional false

                children(dependencies)
            }
        }


        def dependenciesJson = builder.toPrettyString()
        println dependenciesJson
        dependenciesFile << dependenciesJson
    }
}
