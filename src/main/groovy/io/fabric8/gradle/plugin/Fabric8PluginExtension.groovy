package io.fabric8.gradle.plugin

import groovy.transform.ToString

/**
 * Plugin settings
 * @author sigge
 * @since 2014-06-16 13:34
 */
@ToString
class Fabric8PluginExtension {

    String profile = ""
    String[] parentProfiles = ["default"]
    String group = ""
    String version = ""
    String jolokiaUrl = "http://localhost:8181/jolokia"
    String features = []
    String[] featureRepositories = []
    int minInstanceCount = 1
    String serverId
    String baseVersion
    String[] bundles = []
    boolean upload = true
    boolean ignore = false
    boolean includeArtifact = true
    @Deprecated // Use plugin convention
    String profileConfigDir = "src/main/fabric8"
    boolean includeReadMe = true
    String sampleDataDir = "src/main/resources/data"
    boolean includeSampleData = true

    // Internal
    File profileDir

}

