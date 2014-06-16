package io.fabric8.gradle.util

import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author sigge
 * @since 2014-06-13 13:18
 */
class ProfileUtilTest extends Specification {

    @Unroll("Profilename #profilename should give path #path")
    def "test parse profilename should return path"() {
        expect:
            path ==  ProfileUtil.parseProfilenameIntoPath(profilename)
        where:
            profilename         | path
            "my-test-profile"   | "my/test/profile"
            "profile"           | "profile"
            ""                  | ""
    }
}
