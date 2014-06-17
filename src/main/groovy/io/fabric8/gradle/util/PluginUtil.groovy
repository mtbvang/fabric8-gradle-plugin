package io.fabric8.gradle.util

import org.gradle.api.Project

/**
 * @author sigge
 * @since 2014-06-13 13:18
 */
class PluginUtil {
    /**
     * Parses a profilename into a filesystem path (relative)
     * @param profilename
     * @return
     */
    static def parseProfilenameIntoPath(String profilename) {
        return profilename.split('-').join('/')
    }

    /**
     * Checks if it's a war build by looking for war task, else it's a jar
     * @param project
     * @return "war" or "jar"
     */
    static def determinePackaging(Project project) {
        project.tasks.findByPath("war") != null ? "war" : "jar"
    }
}
