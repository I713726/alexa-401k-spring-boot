package voya401k;

public interface ResponseBuilder {
    /**
     * Builds a JSON response out of a Voya Response.
     * @param response
     * @return
     */
    String buildResponse(VoyaResponse response);
}
