package io.fabric8.gradle.plugin

import groovy.transform.ToString

/**
 * Plugin settings
 * @author sigge
 * @since 2014-06-16 13:34
 */
@ToString
class Fabric8PluginExtension {

    String profile
    String parentProfile = "default"
    String jolokiaUrl = "http://localhost:8181/jolokia"
    String features // space separated list
    String featureRepos // space separated list
    int minInstanceCount
    String serverId
    String baseVersion
    String bundles // space separated list
    boolean upload = true
    boolean ignore = false
    boolean includeArtifact = true
    String profileConfigDir = "src/main/fabric8"
    boolean includeReadMe = true
    String sampleDataDir = "src/main/resources/data"
    boolean includeSampleData = true

}

