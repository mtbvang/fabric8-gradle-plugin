package io.fabric8.gradle.task
import groovy.json.JsonBuilder
import org.gradle.api.tasks.TaskAction
import org.jolokia.client.request.J4pExecRequest

import javax.management.ObjectName
/**
 * @author sigge
 * @since 2014-06-11 20:16
 */
class DeployFabric8ProfileTask extends JolokiaClientBaseTask {

    @TaskAction
    def deploy() {
        def settings = project.fabric8
        def profilePath = getProfileDir(project)
        project.logger.info("Deploying profile {$settings.profile} to ${settings.jolokiaUrl}")

        def requirementsBuilder = uploadRequirements(settings)
        uploadConfigurationFile(settings, requirementsBuilder)
        //uploadReadmeFile(settings, requirementsBuilder)
        //refreshProfile(settings)
    }

    def uploadRequirements(settings) {
        def jsonBuilder = buildRequirements(settings)
        def request = new J4pExecRequest(FABRIC_MBEAN, "deployProjectJson", jsonBuilder)
        assert client : "client not set"
        def response = client.execute(request, 'POST')
        println "Got $response"
        response
    }


    def uploadConfigurationFile(settings, data) {
        String version = settings.version
        String profileId = settings.profile
        String path = ""
        def request = new J4pExecRequest(FABRIC_MBEAN, "setConfigurationFile", version, profileId, path, data)
        client.execute(request)
    }

    def refreshProfile(String profileId, String versionId) {
        def mbeanName = new ObjectName(FABRIC_MBEAN);
        if (!profileId.isEmpty() && !versionId.isEmpty()) {
            project.logger.info("Performing profile refresh on mbean: $mbeanName version: $versionId profile: profileId")
            def request = new J4pExecRequest(mbeanName, "refreshProfile", versionId, profileId)
            def response = client.execute(request, "POST")
            response.value
        }
    }

    def uploadReadmeFile(settings, requirementsBuilder) {}

    def buildRequirements(settings) {
        def jsonBuilder = new JsonBuilder()
        jsonBuilder (
            type: 'write',
            mbean: FABRIC_MBEAN,
            profileId: settings.profile,
            version: settings.version,
            baseVersion: settings.baseVersion,
            parentProfiles: settings.parentProfiles,
            bundles: settings.bundles,
            featureRepositories: settings.featureRepositories,
            rootDependency: "$project.group:$project.name:$project.version"
        )
        jsonBuilder
    }

}
