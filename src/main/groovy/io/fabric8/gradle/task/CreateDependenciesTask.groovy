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
    def buildDependenciesFile() {
        logger.info("Creating profile dependencies")
        def fabric8 = project.fabric8

        createBuildDirIfNotExists()
        def dependenciesFile = createDependenciesFile()

        def dependencies = getDependencies(project)
        def dependenciesJson = buildDependenciedJson(fabric8.profile, fabric8.parentProfile, dependencies)
        println dependenciesJson
        dependenciesFile << dependenciesJson
    }

    def buildDependenciedJson(profile, parentProfile, dependencies) {
        def builder = new JsonBuilder()
        builder {
            profileId profile
            parentProfiles parentProfile
            rootDependency {
                groupId project.ext.group
                artifactId project.name
                version project.ext.version
                type PluginUtil.determinePackaging(project)
                optional false

                children(dependencies)
            }
        }


        return builder.toPrettyString()
    }

    def createBuildDirIfNotExists() {
        if (!project.file(project.buildDir.path).exists()) {
            project.file(project.buildDir.path).mkdir()
        }
    }

    def createDependenciesFile() {
        def dependenciesFile = project.file(project.buildDir.path + "/dependencies.json")
        dependenciesFile.createNewFile()
        dependenciesFile
    }

    def getDependencies(project) {
        def dependencies = [] as List
        // Loop through all dependencies and write to file
        project.configurations.all({ configuration ->
            configuration.dependencies.each { dependency ->
                dependencies << [
                        groupId   : dependency.group,
                        artifactId: dependency.name,
                        //classifier: dependency.classifier,
                        scope     : configuration.name,
                        version   : dependency.version,
                        //optional: dependency.optional
                ]
            }
        })
        return dependencies
    }
}
