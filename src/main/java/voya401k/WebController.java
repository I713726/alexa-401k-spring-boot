package voya401k;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    //I undestand how the request mapping works, mostly, but need to figure out the response mapping.
    @RequestMapping(value = "/voya401k", method = RequestMethod.POST)
    @ResponseBody
    public String process(@RequestBody String payload) throws Exception {
        AlexaRequestAndResponseBuilder builder = new AlexaRequestAndResponseBuilder();
        VoyaRequest request = builder.buildRequest(payload);
        VoyaResponse response = new VoyaControllerImpl().getResponse(request);
        String jsonResponse = builder.buildRespose(response);
        return jsonResponse;
    }
}
