package io.fabric8.fabric8.gradle.util

/**
 * @author sigge
 * @since 2014-06-13 13:18
 */
class ProfileUtil {
    static def parseProfilenameIntoPath(String profilename) {
        return profilename.split('-').join('/')
    }
}
