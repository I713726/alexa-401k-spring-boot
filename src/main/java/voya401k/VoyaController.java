package voya401k;

/**
 * This is a pretty straightforaward interface, it has one method that takes a VoyaRequest and produces the appropriate VoyaResponse
 */
public interface VoyaController{
    /**
     * Return the proper response for the given VoyaReqest
     * @param sessionData The request (created based on posted JSON)
     * @return
     */
    public VoyaResponse getResponse(VoyaRequest sessionData);
}
