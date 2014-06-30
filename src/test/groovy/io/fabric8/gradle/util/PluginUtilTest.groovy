package io.fabric8.gradle.util
import io.fabric8.gradle.BaseSpecification
import org.gradle.api.tasks.bundling.War
import spock.lang.Unroll

import static io.fabric8.gradle.util.PluginUtil.splitIntoList

/**
 * @author sigge
 * @since 2014-06-13 13:18
 */
class PluginUtilTest extends BaseSpecification {


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

    def "Merge property files should give a map with propeyry key/values"() {
        given:
        // Could use GroovyClassLoader
            def file1 = new File("src/test/resources/properties1.properties")
            def file2 = new File("src/test/resources/properties2.properties")
            def file3 = new File("src/test/resources/properties3.properties")
        when:
            def result = PluginUtil.mergePropertyFiles([file1, file2, file3])
        then:
            result == [avalue11: 'a value 111', avalue12: 'a value 12', avalue21: 'a value 211', avalue22: 'a value 22', avalue31: 'a value 31', avalue32: 'a value 32']

    }
    def "Merge property files into file should give a map with propetry key/values in the destination file"() {
        given:
        // Could use GroovyClassLoader
            def dest = new File("build/tmp/test-merged.properties")
            def file1 = new File("src/test/resources/properties1.properties")
            def file2 = new File("src/test/resources/properties2.properties")
            def file3 = new File("src/test/resources/properties3.properties")
        when:
            PluginUtil.mergePropertyFilesIntoFile([file1, file2, file3], dest)
            def loadedProperties = new Properties()
            dest.withInputStream { stream ->
                loadedProperties.load(stream)
            }
        then:
            loadedProperties.sort() == [avalue11: 'a value 111', avalue12: 'a value 12', avalue21: 'a value 211', avalue22: 'a value 22', avalue31: 'a value 31', avalue32: 'a value 32']
    }

    @Unroll("#spaceSeparated should be split into #result")
    def "test split list #spaceSeparated should equal list #result"() {
        expect:
            splitIntoList(spaceSeparated) == result
        where:
            spaceSeparated  | result
            ""              | []
            "value1 value2" | ["value1", "value2"]
            "value1  value2"| ["value1", "value2"]

    }
}
