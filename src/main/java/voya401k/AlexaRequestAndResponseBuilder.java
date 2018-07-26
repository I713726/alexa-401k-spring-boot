package voya401k;

import org.json.JSONException;
import org.json.JSONObject;

public class AlexaRequestAndResponseBuilder implements VoyaRequestAndResponseBuilder{


    @Override
    public VoyaRequest buildRequest(String jsonData) {
        //TODO: What if the elements don't exist yet?
        JSONObject jsonObject = new JSONObject(jsonData);
        int questionNo;
        int voyaPIN;
        VoyaIntentType intentType;
        try{
            questionNo = Integer.parseInt(jsonObject.getJSONObject("session").getJSONObject("attributes").getString("questionNo"));
        }
        catch(JSONException e) {
            questionNo = 0;
        }

        try {
            voyaPIN = Integer.parseInt(jsonObject.getJSONObject("session").getJSONObject("attributes").getString("voyaPIN"));
        }
        catch(JSONException e) {
            voyaPIN = 0;
        }
        try{
            intentType = this.getIntentType(jsonObject.getJSONObject("request").getJSONObject("intent").getString("name"));
        }
        catch(JSONException e) {
            //TODO: When the request isn't an intent, should probably do something better than this
            intentType = null;
        }


        VoyaRequestType requestType = this.getRequestType(jsonObject.getJSONObject("request").getString("type"));
        String locale = jsonObject.getJSONObject("request").getString("locale");

        return new VoyaRequestImpl(questionNo, voyaPIN, requestType, locale, intentType);
    }

    @Override
    public String buildRespose(VoyaResponse response) {
        JSONObject outJson = new JSONObject();
        outJson.put("version", 1.0);
        outJson.put("sessionAttributes", new JSONObject().put("questionNo", response.getQuestionNumber())
                .put("voyaPin", response.getUserPIN()));
        outJson.put("response", new JSONObject().put("outputSpeech", new JSONObject().put("type", "SSML")
                .put("ssml", response.getSpeech())));
        outJson.put("reprompt", new JSONObject().put("outputSpeech", new JSONObject().put("type", "SSML")
                .put("ssml", response.getReprompt())));
        outJson.put("shouldEndSession", response.getShouldSessionEnd());

        return outJson.toString();
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
            default:
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
            default:
                return VoyaIntentType.QUIT;
        }
    }
}
