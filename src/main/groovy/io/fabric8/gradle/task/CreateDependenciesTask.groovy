package io.fabric8.gradle.task
import groovy.json.JsonBuilder
import io.fabric8.gradle.util.PluginUtil
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

/**
 * @author sigge
 * @since 2014-06-11 20:16
 */
class CreateDependenciesTask extends BaseTask {

    @OutputFile
    def File dependenciesFile = project.file(destDir.path + "/dependencies.json")

    @TaskAction
    def buildDependenciesFile() {
        logger.info("Creating profile dependencies")
        def fabric8 = project.fabric8

        def dependencies = getDependencies(project)
        def dependenciesJson = buildDependenciedJson(fabric8, dependencies)
        println dependenciesJson
        dependenciesFile << dependenciesJson
    }

    def buildDependenciedJson(fabric8, dependencies) {
        def builder = new JsonBuilder()
        builder {
            profileId fabric8.profile
            parentProfiles fabric8.parentProfile
            rootDependency {
                groupId fabric8.group
                artifactId project.name
                version fabric8.version
                type PluginUtil.determinePackaging(project)
                optional false

                children(dependencies)
            }
        }
        return builder.toPrettyString()
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
