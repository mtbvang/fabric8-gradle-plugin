package io.fabric8.gradle.plugin
import io.fabric8.gradle.task.CreateDependenciesTask
import io.fabric8.gradle.task.CreateFabric8AgentPropertiesTask
import io.fabric8.gradle.task.CreateProfileTask
import io.fabric8.gradle.task.DeployFabric8ProfileTask
import io.fabric8.gradle.task.GetFabric8MavenRepositoryTask
import io.fabric8.gradle.task.PackageResourcesTask
import io.fabric8.gradle.util.JolokiaClient
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Upload
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
        configureCreateProfileDirectory(project)
        configurePackageResources(project)

        configureCreateFabric8AgentProperties(project)
        configureCreateDependencies(project)

        configureArchiveProfile(project)

        configureFabric8MavenRepoUri(project)
        configureUploadProfile(project)
        configureDeployProfile(project)
    }

    def configureCreateProfileDirectory(Project project) {
        project.task('createProfile', type: CreateProfileTask, dependsOn: 'install') {
            project.logger.debug("Creating createProfile task")
        }
    }
    def configurePackageResources(Project project) {
        project.task('packageResources', type: PackageResourcesTask, dependsOn: 'createProfile') << {
            project.logger.info("Creating PackageResources task: ")
        }
    }

    def configureCreateFabric8AgentProperties(Project project) {
        project.task('createFabric8AgentProperties', type: CreateFabric8AgentPropertiesTask, dependsOn: 'packageResources') {
            project.logger.debug("Creating CreateFabric8AgentProperties task")
        }
    }

    def configureCreateDependencies(Project project) {
        project.task('createDependencies', type: CreateDependenciesTask, dependsOn: 'createFabric8AgentProperties') {
            project.logger.debug("Creating CreateDependencies task")
        }
    }

    def configureArchiveProfile(Project project) {
        project.task('archiveFabric8Profile', type: Zip, dependsOn: 'createFabric8AgentProperties') {
            project.logger.info("Creating ArchiveFabric8Profile task: ")
            from "${project.buildDir}/generated"
        }
    }

    def configureFabric8MavenRepoUri(Project project) {
        project.task('configureFabric8MavenRepoUri', type: GetFabric8MavenRepositoryTask, dependsOn: 'archiveFabric8Profile') {
            project.logger.info("Creating configureFabric8MavenRepoUri task: ")
            String serverUrl = System.properties."fabric8.server.url"
            String jolokiaPath = System.properties."fabric8.server.jolokia.path"
            String user = System.properties."fabric8.server.user"
            String password = System.properties."fabric8.server.password"
            client = new JolokiaClient(serverUrl+jolokiaPath, user, password)
        }
    }

    def configureUploadProfile(Project project) {
        project.task('uploadFabric8Profile', type: Upload, dependsOn: 'configureFabric8MavenRepoUri') {
            project.logger.info("Creating deployFabric8Profile task: ")
            String serverUrl = System.properties."fabric8.server.url"
            String jolokiaPath = System.properties."fabric8.server.jolokia.path"
            String user = System.properties."fabric8.server.user"
            String password = System.properties."fabric8.server.password"
            client = new JolokiaClient(serverUrl+jolokiaPath, user, password)
        }
    }

    def configureDeployProfile(Project project) {
        project.task('deployFabric8Profile', type: DeployFabric8ProfileTask, dependsOn: 'deployFabric8Profile') {
            project.logger.info("Creating deployFabric8Profile task: ")
            String serverUrl = System.properties."fabric8.server.url"
            String jolokiaPath = System.properties."fabric8.server.jolokia.path"
            String user = System.properties."fabric8.server.user"
            String password = System.properties."fabric8.server.password"
            client = new JolokiaClient(serverUrl+jolokiaPath, user, password)
        }
    }

}
