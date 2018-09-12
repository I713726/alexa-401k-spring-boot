package voya401k;

import org.springframework.web.bind.annotation.*;

/**
 * This class controls the request mapping, accepting and receiving JSON. It would be relatively easy to have different
 * URLs for different types of virtual assistants, e.g. a URL for Alexa, a URL for Google Home, ect.
 */

@RestController
public class WebController {

    /**
     * This method takes JSON from the post from the virtual assistant, processes it, and retrns the appropriate response.
     *
     * @param payload The JSON sent in the POST request
     * @return JSON response for processing by virtual assistant.
     */
    @RequestMapping(value = "/voya401k", method = RequestMethod.POST)
    @ResponseBody
    public String process(@RequestBody String payload) {
        AlexaRequestAndResponseBuilder builder = new AlexaRequestAndResponseBuilder();
        VoyaRequest request = builder.buildRequest(payload);
        VoyaResponse response = new VoyaControllerImpl().getResponse(request);
        String jsonResponse = builder.buildResponse(response);
        return jsonResponse;
    }

    @RequestMapping(value = "/voya401k-visual", method = RequestMethod.POST)
    @ResponseBody
    public String visualSkillProcess(@RequestBody String payload) {
        EchoShowRequestAndResponseBuilder builder = new EchoShowRequestAndResponseBuilder();
        VoyaRequest request = builder.buildRequest(payload);
        VoyaResponse response = new VoyaControllerImpl().getResponse(request);
        String jsonResponse = builder.buildResponse(response);
        return jsonResponse;
    }
}
