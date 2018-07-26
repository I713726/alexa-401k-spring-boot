package voya401k;

import java.util.Map;

public interface VoyaRequestAndResponseBuilder {

    /**
     * This method parses JSON data from a virtual assistant and uses it to create a VoyaRequest object
     * @param jsonData
     * @return
     */
    VoyaRequest buildRequest(String jsonData);

    /**
     * This method takes a VoyaResponse object and uses it to create a JSON string to be sent to the virtual assistant.
     * @param response
     * @return
     */
    String buildRespose(VoyaResponse response);
}
