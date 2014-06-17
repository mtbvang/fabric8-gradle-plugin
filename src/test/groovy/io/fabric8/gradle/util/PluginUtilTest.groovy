package io.fabric8.gradle.util

import org.gradle.api.tasks.bundling.War
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification
import spock.lang.Unroll
/**
 * @author sigge
 * @since 2014-06-13 13:18
 */
class PluginUtilTest extends Specification {
    def project

    void setup() {
        project = ProjectBuilder.builder().withName("fabric8-gradle-plugin").build()
    }

    @Unroll("Profilename #profilename should give path #path")
    def "test parse profilename should return path"() {
        expect:
            path ==  PluginUtil.parseProfilenameIntoPath(profilename)
        where:
            profilename         | path
            "my-test-profile"   | "my/test/profile"
            "profile"           | "profile"
            ""                  | ""
    }

    def "Packaging should be jar"() {
        expect:
            assert "jar" == PluginUtil.determinePackaging(project)
    }

    def "Packaging should be war"() {
        given:
            project.task('war', type: War)
        expect:
            assert "war" == PluginUtil.determinePackaging(project)
    }
}
