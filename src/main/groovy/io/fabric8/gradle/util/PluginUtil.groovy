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

    /**
     * Merge property files contents into a Property object
     * @param files
     * @return Property
     */
    static def mergePropertyFiles(List<File> files) {
        def properties = new Properties()
        files.each { file ->
            def props = new Properties()
            file.withInputStream { stream ->
                props.load(stream)
            }
            properties.putAll(props)
        }
        properties
    }

    /**
     * Merge property files contents into new Property file
     * @param files
     * @param dest destination file
     */
    static def mergePropertyFilesIntoFile(List<File> files, File dest) {
        def properties = mergePropertyFiles(files)
        dest.withWriter { writer ->
            properties.store(writer, null)
        }
    }

    /**
     * Splits a space delimited string into a list of values
     * @param spaceSeparated
     */
    static def splitIntoList(String spaceSeparated) {
        spaceSeparated.isEmpty() ? [] : spaceSeparated.replaceAll("\\s\\s", " ").split(" ")
    }
}
