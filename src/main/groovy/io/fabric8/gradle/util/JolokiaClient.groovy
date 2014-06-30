package io.fabric8.gradle.util

import org.jolokia.client.J4pClient
/**
 * @author sigge
 * @since 2014-06-30 11:26
 */
class JolokiaClient {
    @Delegate
    J4pClient client

    JolokiaClient(String jolokiaUrl, String username, String password) {
        this.client = createJolokiaClient(jolokiaUrl, username, password)
    }

    def createJolokiaClient(String jolokiaUrl, String username, String password)  {
        if (!username || username.isEmpty()) {
            throw new IllegalArgumentException("No <username> value defined for the server. Please configure one")
        }
        if (!password || password.isEmpty()) {
            throw new IllegalArgumentException("No <password> value defined for the server  Please configure one")
        }
        return J4pClient.url(jolokiaUrl).user(username).password(password).build()
    }

}
