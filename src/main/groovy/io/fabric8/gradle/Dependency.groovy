package io.fabric8.gradle

/**
 * Represents a maven/gradle dependency
 * @author sigge
 * @since 2014-06-17 13:35
 */
class Dependency {

    String groupId
    String artifactId
    String version
    String classifier
    String scope
    String type
    boolean optional
}
