package voya401k;

import java.util.Map;

public interface VoyaRequestBuilder {
    VoyaRequest build(Map<String, Object> jsonData);
}
