package io.fabric8.gradle.task
import org.gradle.api.tasks.TaskAction
import org.jolokia.client.request.J4pReadRequest
/**
 * @author sigge
 * @since 2014-06-11 20:16
 */
class GetFabric8MavenRepositoryTask extends JolokiaClientBaseTask {

    @TaskAction
    def getMavenUploadUri() {
        def request = new J4pReadRequest(FABRIC_MBEAN, "MavenRepoUploadURI")

        assert client: "client not set"
        def response = client.execute(request).asJSONObject()
        logger.debug("Got response ${response}")
        if(response.get("status") != 200) {
            logger.error("Failed to get maven upload repository uri from ${client.uri}. Host down?")
        }
        def mavenUploadUri = response.get("value")
        logger.debug("Got Maven Upload URI ${mavenUploadUri}")
        return mavenUploadUri
    }

}
