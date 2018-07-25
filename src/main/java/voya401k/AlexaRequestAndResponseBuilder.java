package voya401k;

import java.util.Map;
import org.json.JSONObject;

public class AlexaRequestAndResponseBuilder implements VoyaRequestAndResponseBuilder{

    @Override
    public VoyaRequest build(String jsonData) {
        JSONObject jsonObject = new JSONObject(jsonData);
        int questionNo = Integer.parseInt(jsonObject.getJSONObject("session").getJSONObject("attributes").getString("questionNo"));
        int voyaPIN = Integer.parseInt(jsonObject.getJSONObject("session").getJSONObject("attributes").getString("voyaPIN"));
        VoyaRequestType requestType = this.getRequestType(jsonObject.getJSONObject("request").getString("type"));
        String locale = jsonObject.getJSONObject("request").getString("locale");
        VoyaIntentType intentType = this.getIntentType(jsonObject.getJSONObject("request").getJSONObject("intent").getString("name"));
    }

    @Override
    public String buildJSON(VoyaResponse response) {

    }

    private VoyaRequestType getRequestType(String requestType) {
        //TODO: MAKE SURE THESE STRINGS ARE RIGHT, I'M NOT SURE HOW BUT CHECK
        switch(requestType) {
            case "LaunchRequest":
                return VoyaRequestType.LAUNCH_REQUEST;
            case "IntentRequest":
                return VoyaRequestType.INTENT_REQUEST;
            case "HelpRequest":
                return VoyaRequestType.HELP_REQUEST;
            case "SessionEndedRequest":
                return VoyaRequestType.SESSION_END_REQUEST;
            case "StopRequest":
                return VoyaRequestType.STOP_REQUEST;
            case "CancelRequest":
                return VoyaRequestType.CANCEL_REQUEST;
        }
    }

    private VoyaIntentType getIntentType(String intentType) {
        switch(intentType) {
            case "VoyaHowMyAccountIntent":
                return VoyaIntentType.SUMMARY;
            case "VoyaNoIntent":
                return VoyaIntentType.NO;
            case "VoyaPINIntent":
                return VoyaIntentType.PIN;
            case "VoyaYesIntent":
                return VoyaIntentType.YES;
            case "VoyaQuitIntent":
                return VoyaIntentType.QUIT;
        }
    }
}
