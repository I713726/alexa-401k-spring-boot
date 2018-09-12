package voya401k;

import com.fasterxml.jackson.core.io.JsonStringEncoder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class EchoShowRequestAndResponseBuilder extends AlexaRequestAndResponseBuilder {

    @Override
    public String buildResponse(VoyaResponse response) {
        JSONObject outJson = new JSONObject();
        JsonStringEncoder encoder = new JsonStringEncoder();
        outJson.put("version", 1.0);
        outJson.put("response", new JSONObject().put("outputSpeech", new JSONObject().put("type", "SSML")
                .put("ssml", "<speak>" + response.getSpeech() + "</speak>")));
        outJson.getJSONObject("response").put("reprompt", new JSONObject().put("outputSpeech",
                new JSONObject().put("type", "SSML").put("ssml", ("<speak>" + response.getReprompt() + "</speak>"))));

        outJson.put("sessionAttributes", new JSONObject().put("voyaPin", response.getUserPIN()));
        outJson.getJSONObject("sessionAttributes").put("questionNo", response.getQuestionNumber())
                .put("voyaPin", response.getUserPIN());
        outJson.getJSONObject("sessionAttributes").put("notificationNo", response.getNotificationNumber());

        //testing video play
        /*
        outJson.getJSONObject("response").put("directives", new JSONArray().put(
                new JSONObject().put("type", "VideoApp.Launch").put("videoItem", new JSONObject().put("source", "https://drive.google.com/uc?export=download&id=0B5GHSc8KvSS8c2tTYWNSWm1LanM")
                .put("metadata", new JSONObject().put("title", "retirement day video").put("subtitle", "subtitle")))));

        */

        outJson.getJSONObject("response").put("directives", new JSONArray().put(
                new JSONObject().put("type", "BodyTemplate1").put("token", "t123").put("backButton", "hidden")
                .put("backgroundImage", new JSONObject().put("contentDescription", "voyaLogo")
                                                        .put("sources", new JSONArray().put(new JSONObject().put("url", "https://www.voya.com/sites/all/themes/custom/voya_base_theme/social_logo.jpg")))
                ).put("title", "Voya 401k").put("textContent", new JSONObject().put("primaryText", new JSONObject().put("text", "cardInfo").put("type", "PlainText")))

        ));



        return outJson.toString();
    }
}
