package io.fabric8.gradle.task

import groovy.text.GStringTemplateEngine
import io.fabric8.gradle.util.ProfileUtil
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

/**
 * Creates the io.fabric8.agent.properties file
 * @author sigge
 * @since 2014-06-16 16:19
 */
class CreateFabric8AgentPropertiesTask extends BaseTask {

    def static final String templateUri = "/templates/io.fabric8.agent.properties.template"

    @TaskAction
    def createFabric8AgentPropertiesFile() {
        def fabric8 = project.fabric8
        def profilePath = destDir.path + "/" + ProfileUtil.parseProfilenameIntoPath(fabric8.profile)
        logger.debug("Creating io.fabric8.agent.properties file")
        def agentPropertiesFile = project.file("$profilePath/io.fabric8.agent.properties")

        def templateEngine = new GStringTemplateEngine()
        def templateReader = this.class.getResourceAsStream(templateUri).newReader()
        def profile = [
                name: fabric8.profile,
                parent: [profiles: fabric8.parentProfile]
        ]
        def template = templateEngine.createTemplate(templateReader).make([profile: profile, packaging: determinePackaging(project), groupId: project.group, artifactId: project.name, version: project.version])
        def text = template.toString()
        project.file(agentPropertiesFile).write(text)
    }

    /**
     * Checks if it's a war build by looking for war task, else it's a jar
     * @param project
     * @return "war" or "jar"
     */
    def determinePackaging(Project project) {
        project.tasks.findByPath("war") != null ? "war" : "jar"
    }
}
