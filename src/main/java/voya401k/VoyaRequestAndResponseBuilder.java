package voya401k;

import java.util.Map;

public interface VoyaRequestAndResponseBuilder {
    VoyaRequest build(String jsonData);

    String buildJSON(VoyaResponse response);
}
