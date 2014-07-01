package io.fabric8.gradle.task

import io.fabric8.gradle.util.JolokiaClient

/**
 * @author sigge
 * @since 2014-07-01 14:30
 */
abstract class JolokiaClientBaseTask extends BaseTask {
    String FABRIC_MBEAN = "io.fabric8:type=Fabric"

    JolokiaClient client

    void setClient(JolokiaClient client) {
        if (!this.client) {
            logger.debug("Setting client")
            this.client = client
        }
    }


}
