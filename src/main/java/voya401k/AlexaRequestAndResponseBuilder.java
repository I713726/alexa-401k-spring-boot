package voya401k;

import org.json.JSONException;
import org.json.JSONObject;
import com.fasterxml.jackson.core.io.JsonStringEncoder;

public class AlexaRequestAndResponseBuilder implements VoyaRequestAndResponseBuilder{


    @Override
    public VoyaRequest buildRequest(String jsonData) {
        JSONObject jsonObject = new JSONObject(jsonData);
        int questionNo;
        int voyaPIN;
        VoyaIntentType intentType;
        try{
            questionNo = jsonObject.getJSONObject("session").getJSONObject("attributes").getInt("questionNo");
        }
        catch(JSONException e) {
            questionNo = 0;
        }

        try{
            intentType = this.getIntentType(jsonObject.getJSONObject("request").getJSONObject("intent").getString("name"));
        }
        catch(JSONException e) {
            //TODO: When the request isn't an intent, should probably do something better than this
            intentType = null;
        }
        if(intentType == VoyaIntentType.PIN) {
            try {
                voyaPIN = Integer.parseInt(
                        jsonObject.getJSONObject("request").getJSONObject("intent").getJSONObject("slots")
                                .getJSONObject("pin").getString("value")
                );
            }
            catch(JSONException e) {
                throw new IllegalArgumentException("No PIN in PIN intent!");
            }
            catch(NumberFormatException e) {
                voyaPIN = 0;
            }
       }
        else {
            try {
                voyaPIN = jsonObject.getJSONObject("session").getJSONObject("attributes").getInt("voyaPin");
            }
            catch(JSONException e) {
                voyaPIN = 0;
            }
        }

        VoyaRequestType requestType = this.getRequestType(jsonObject.getJSONObject("request").getString("type"));
        String locale = jsonObject.getJSONObject("request").getString("locale");

        return new VoyaRequestImpl(questionNo, voyaPIN, requestType, locale, intentType);
    }

    @Override
    public String buildResponse(VoyaResponse response) {
        JSONObject outJson = new JSONObject();
        JsonStringEncoder encoder = new JsonStringEncoder();
        outJson.put("version", 1.0);
        outJson.put("response", new JSONObject().put("outputSpeech", new JSONObject().put("type", "SSML")
                .put("ssml", "<speak>" + response.getSpeech() + "</speak>")));
        outJson.getJSONObject("response").put("reprompt", new JSONObject().put("outputSpeech",
                new JSONObject().put("type", "SSML").put("ssml", ("<speak>" + response.getReprompt() + "</speak>"))));
        outJson.getJSONObject("response").put("shouldEndSession", response.getShouldSessionEnd());
        outJson.put("sessionAttributes", new JSONObject().put("questionNo", response.getQuestionNumber())
                .put("voyaPin", response.getUserPIN()));
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
