package io.fabric8.gradle.plugin
import io.fabric8.gradle.task.CreateDependenciesTask
import io.fabric8.gradle.task.CreateFabric8AgentPropertiesTask
import io.fabric8.gradle.task.CreateProfileTask
import io.fabric8.gradle.task.PackageResourcesTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Zip
/**
 * @author sigge
 * @since 2014-06-11 19:42
 */
class Fabric8Plugin implements Plugin<Project>{

    void apply(Project project) {
        // Set up plugin properties
        project.extensions.create("fabric8", Fabric8PluginExtension)
        Fabric8PluginConvention convention = new Fabric8PluginConvention(project)
        // TODO apparently extensions are prefered over conventions
        project.convention.plugins.fabric8 = convention

        // Create tasks
        createProfileDirectory(project)
        packageResources(project)
        createFabric8AgentProperties(project)

        createDependencies(project)

        archiveProfile(project)
    }

    def createProfileDirectory(Project project) {
        project.task('createProfile', type: CreateProfileTask, dependsOn: 'build') {
            project.logger.debug("Creating createProfile task")
        }
    }
    def packageResources(Project project) {
        project.task('packageResources', type: PackageResourcesTask, dependsOn: 'createProfile') << {
            project.logger.info("Creating PackageResources task: ")
        }
    }

    def createFabric8AgentProperties(Project project) {
        project.task('createFabric8AgentProperties', type: CreateFabric8AgentPropertiesTask, dependsOn: 'packageResources') {
            project.logger.debug("Creating CreateFabric8AgentProperties task")
        }
    }

    def createDependencies(Project project) {
        project.task('createDependencies', type: CreateDependenciesTask, dependsOn: 'createFabric8AgentProperties') {
            project.logger.debug("Creating CreateDependencies task")
        }
    }

    def archiveProfile(Project project) {
        project.task('archiveFabric8Profile', type: Zip, dependsOn: 'createFabric8AgentProperties') {
            project.logger.info("Creating ArchiveFabric8Profile task: ")
            from "${project.buildDir}/generated"
        }
    }

}
