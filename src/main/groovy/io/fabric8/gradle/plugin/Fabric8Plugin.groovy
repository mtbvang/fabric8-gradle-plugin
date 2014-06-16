package io.fabric8.gradle.plugin

import io.fabric8.gradle.task.CreateFabric8AgentPropertiesTask
import io.fabric8.gradle.task.CreateProfileTask
import org.gradle.api.Plugin
import org.gradle.api.Project
/**
 * @author sigge
 * @since 2014-06-11 19:42
 */
class Fabric8Plugin implements Plugin<Project>{

    void apply(Project project) {
        def logger = project.logger

        // Set up plugin properties
        project.extensions.create("fabric8", Fabric8PluginExtension)

        // Create tasks

        project.task('configure', dependsOn: 'processResources') {
            logger.debug("Configuring plugin using properties ${project.extensions.fabric8}")
        }
        project.task('createProfile', type: CreateProfileTask, dependsOn: 'configure') {
            logger.debug("Creating createProfile task")
        }
        project.task('createFabric8AgentProperties', type: CreateFabric8AgentPropertiesTask, dependsOn: 'createProfile') {
            logger.debug("Creating CreateFabric8AgentPropertiesTask task")
        }
    }

}
