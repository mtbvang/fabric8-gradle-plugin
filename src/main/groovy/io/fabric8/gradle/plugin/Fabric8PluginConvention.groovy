package io.fabric8.gradle.plugin

import org.gradle.api.Project

/**
 * Fabric8 Plugin convention
 * @author sigge
 * @since 2014-06-23 14:13
 */
class Fabric8PluginConvention {

    String fabric8SrcDir = "src/main/fabric8"

    final Project project

    Fabric8PluginConvention(Project project) {
        this.project = project
    }
}
