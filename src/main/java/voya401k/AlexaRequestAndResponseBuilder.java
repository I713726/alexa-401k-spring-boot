package voya401k;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.fasterxml.jackson.core.io.JsonStringEncoder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class AlexaRequestAndResponseBuilder implements VoyaRequestAndResponseBuilder{


    @Override
    public VoyaRequest buildRequest(String jsonData) {
        JSONObject jsonObject = new JSONObject(jsonData);
        int questionNo;
        int notificationNumber;
        int voyaPIN;
        Calendar fromDate = new GregorianCalendar();
        Calendar toDate = new GregorianCalendar();
        VoyaIntentType intentType;
        boolean hasDisplay;
        try{
            questionNo = jsonObject.getJSONObject("session").getJSONObject("attributes").getInt("questionNo");
        }
        catch(JSONException e) {
            questionNo = 0;
        }

        try {
            notificationNumber = jsonObject.getJSONObject("session").getJSONObject("attributes")
                    .getInt("notificationNo");
        }
        catch(JSONException e) {
            notificationNumber = 0;
        }

        try{
            intentType = this.getIntentType(jsonObject.getJSONObject("request").getJSONObject("intent").getString("name"));
        }

        catch(JSONException e) {
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
        if(intentType == VoyaIntentType.ACCOUNTACTIVITY) {
            SimpleDateFormat alexaFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                fromDate.setTime(alexaFormat.parse(jsonObject.getJSONObject("request").getJSONObject("intent")
                        .getJSONObject("slots").getJSONObject("fromDate").getString("value")));
            } catch(JSONException e) {
                //then we must be dealing with a number of days, but the number of days will be converted to a
                // date range ending today.
                try {
                    int numDays = jsonObject.getJSONObject("request").getJSONObject("intent").getJSONObject("slots")
                            .getJSONObject("numDays").getInt("value");
                    Calendar cal = GregorianCalendar.getInstance();
                    toDate.setTime(new Date());
                    fromDate = new GregorianCalendar();
                    fromDate.setTime(new Date());
                    fromDate.add(GregorianCalendar.DAY_OF_MONTH, 0 - numDays);
                }
                catch(JSONException ex) {
                    Calendar cal = GregorianCalendar.getInstance();
                    cal.setTime(new Date());
                    cal.add(GregorianCalendar.DAY_OF_MONTH, 0 - 5);
                    fromDate = cal;
                }
            }
            catch (ParseException e) {
                //Not sure what to do here, if the text is an invalid date.
            }
            try {
                toDate.setTime(alexaFormat.parse(jsonObject.getJSONObject("request").getJSONObject("intent")
                        .getJSONObject("slots").getJSONObject("toDate").getString("value")));
            }
            catch(JSONException e) {
                toDate.setTime(new Date());
            }
            catch(ParseException e) {
                toDate.setTime(new Date());
            }
        }

        if(jsonObject.getJSONObject("context").getJSONObject("System").getJSONObject("device")
                .getJSONObject("supportedInterfaces").has("Display")) {
            hasDisplay = true;
        }
        else {
            hasDisplay = false;
        }

        VoyaRequestType requestType = this.getRequestType(jsonObject.getJSONObject("request").getString("type"));
        String locale = jsonObject.getJSONObject("request").getString("locale");

        return new VoyaRequestImpl(questionNo, voyaPIN, requestType, locale, intentType, fromDate,
                toDate, notificationNumber, hasDisplay);
    }

    @Override
    public String buildResponse(VoyaResponse response, VoyaRequest request) {
        JSONObject outJson = new JSONObject();
        JsonStringEncoder encoder = new JsonStringEncoder();
        outJson.put("version", 1.0);
        outJson.put("response", new JSONObject().put("outputSpeech", new JSONObject().put("type", "SSML")
                .put("ssml", "<speak>" + response.getSpeech() + "</speak>")));
        outJson.getJSONObject("response").put("reprompt", new JSONObject().put("outputSpeech",
                new JSONObject().put("type", "SSML").put("ssml", ("<speak>" + response.getReprompt() + "</speak>"))));

        outJson.getJSONObject("response").put("shouldEndSession", response.getShouldSessionEnd());
        outJson.put("sessionAttributes", new JSONObject().put("voyaPin", response.getUserPIN()));
        outJson.getJSONObject("sessionAttributes").put("questionNo", response.getQuestionNumber())
                .put("voyaPin", response.getUserPIN());
        outJson.getJSONObject("sessionAttributes").put("notificationNo", response.getNotificationNumber());

        if(response.getVisualDisplay() != null && request.getVoyaPIN() != 0) {
            outJson.getJSONObject("response").put("directives", new JSONArray().put(
                    new JSONObject().put("template", new JSONObject()).put("type", "Display.RenderTemplate")));

            JSONObject renderTemplateDirective = outJson.getJSONObject("response").getJSONArray("directives")
                    .getJSONObject(0).getJSONObject("template");
            switch(request.getIntent()) {
                case VIEWNOTIFICATIONS:
                    if(response.getVisualDisplay() instanceof VisualList) {
                        VisualList display = (VisualList) response.getVisualDisplay();
                        renderTemplateDirective.put("type", "ListTemplate1");
                        //renderTemplateDirective.put("backgroundImage", response.getVisualDisplay().getBackgroundImageURL());
                        renderTemplateDirective.put("title", response.getVisualDisplay().getTitle());
                        renderTemplateDirective.put("listItems", new JSONArray());
                        /*
                        renderTemplateDirective.put("image", new JSONObject().put("contentDescription", "Voya Logo")
                                .put("sources", new JSONArray().put(new JSONObject()
                                        .put("url", response.getVisualDisplay().getBackgroundImageURL()))));
                        */
                        renderTemplateDirective.put("backgroundImage",
                                new JSONObject().put("sources", new JSONArray().put(new JSONObject().put("url",
                                        response.getVisualDisplay().getBackgroundImageURL()))));
                        for(String listItem : display.getListItems()) {
                            renderTemplateDirective.getJSONArray("listItems").put(
                                    new JSONObject().put("textContent", new JSONObject().put("primaryText",
                                            new JSONObject().put("text", "<font size=\"2\"  >" + listItem + "</font>")
                                                    .put("type", "RichText")))
                            );
                        }
                    }
                    break;
                case ACCOUNTACTIVITY:
                    if(response.getVisualDisplay() instanceof VisualList) {
                        VisualList display = (VisualList) response.getVisualDisplay();
                        renderTemplateDirective.put("type", "ListTemplate1");
                        //renderTemplateDirective.put("backgroundImage", response.getVisualDisplay().getBackgroundImageURL());
                        renderTemplateDirective.put("title", response.getVisualDisplay().getTitle());
                        renderTemplateDirective.put("listItems", new JSONArray());
                        DateFormat alexaFormat = new SimpleDateFormat("MM/dd/yyyy");
                        /*
                        outJson.getJSONObject("response").getJSONArray("directives").getJSONObject(0)
                                .put("backgroundImage", response.getVisualDisplay().getBackgroundImageURL());
                        */
                        renderTemplateDirective.put("backgroundImage", new JSONObject().put("contentDescription", "Voya Logo")
                                .put("sources", new JSONArray().put(new JSONObject()
                                        .put("url", response.getVisualDisplay().getBackgroundImageURL()))));

                        if(display.getListItems().size() == 0) {
                            renderTemplateDirective.getJSONArray("listItems").put(
                                    new JSONObject().put("textContent", new JSONObject().put("primaryText",
                                            new JSONObject().put("text", "<font size=\"2\">No transactions in the " +
                                                    "specified range (" + alexaFormat.format(request.getStartDate()) + " to "
                                                    + alexaFormat.format(request.getEndDate()) + ")</font>").put("type", "RichText")))
                            );
                        }
                        for(String listItem : display.getListItems()) {
                            renderTemplateDirective.getJSONArray("listItems").put(
                                    new JSONObject().put("textContent", new JSONObject().put("primaryText",
                                            new JSONObject().put("text", "<font size=\"2\">" + listItem + "</font>").put("type", "RichText")))
                            );
                        }
                    }
                    break;
                case SUMMARY:
                    renderTemplateDirective.put("type", "BodyTemplate2");
                    renderTemplateDirective.put("image", new JSONObject().put("contentDescription", "Voya Logo")
                            .put("sources", new JSONArray().put(new JSONObject()
                                    .put("url", response.getVisualDisplay().getForegroundImageURL()))));
                    renderTemplateDirective.put("backgroundImage", new JSONObject().put("contentDescription", "Voya Logo")
                            .put("sources", new JSONArray().put(new JSONObject()
                                    .put("url", response.getVisualDisplay().getBackgroundImageURL()))));
                    renderTemplateDirective.put("title", response.getVisualDisplay().getTitle());
                    renderTemplateDirective.put("textContent", new JSONObject()
                            .put("primaryText", new JSONObject().put("text", response.getVisualDisplay().getPrimaryText())
                                    .put("type", "PlainText"))
                            .put("secondaryText", new JSONObject().put("text", response.getVisualDisplay().getSecondaryText())
                                    .put("type", "PlainText"))
                            .put("tertiaryText", new JSONObject().put("text", response.getVisualDisplay().getTertiaryText())
                                    .put("type", "PlainText")));
                    break;
            }

        }

        System.out.println(outJson.toString());
        return outJson.toString();
        /*
        return "{\n" +
                "  \"version\": \"1.0\",\n" +
                "  \"sessionAttributes\": {\n" +
                "    \"supportedHoroscopePeriods\": {\n" +
                "      \"daily\": true,\n" +
                "      \"weekly\": false,\n" +
                "      \"monthly\": false\n" +
                "    }\n" +
                "  },\n" +
                "  \"response\": {\n" +
                "    \"card\": null,\n" +
                "    \"outputSpeech\": {\n" +
                "      \"type\": \"PlainText\",\n" +
                "      \"text\": \"You are going to have a good day today.\"\n" +
                "    },\n" +
                "    \"reprompt\": {\n" +
                "      \"outputSpeech\": {\n" +
                "        \"type\": \"PlainText\",\n" +
                "        \"text\": \"Anything else?\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"directives\": [\n" +
                "      {\n" +
                "        \"type\": \"Display.RenderTemplate\",\n" +
                "        \"template\": {\n" +
                "          \"type\": \"BodyTemplate1\",\n" +
                "          \"token\": \"horoscope\",\n" +
                "          \"title\": \"This is your horoscope\",\n" +
                "          \"image\": {\n" +
                "            \"contentDescription\": \"Aquarius\",\n" +
                "            \"sources\": [\n" +
                "              {\n" +
                "                \"url\": \"https://example.com/resources/card-images/aquarius-symbol.png\"\n" +
                "              }\n" +
                "            ]\n" +
                "          },\n" +
                "          \"textContent\": {\n" +
                "            \"primaryText\": {\n" +
                "              \"type\": \"RichText\",\n" +
                "              \"text\": \"You are going to have a <b>good day</b> today.\"\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    ],\n" +
                "    \"shouldEndSession\": false\n" +
                "  }\n" +
                "}";
                */
    }

    private VoyaRequestType getRequestType(String requestType) {
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
            case "VoyaRateOfREturnIntent":
                return VoyaIntentType.RATEOFRETURN;
            case "VoyaRAteOfReturnIntent":
                return VoyaIntentType.RATEOFRETURN;
            case "VoyaRetirementAgeIntent":
                return VoyaIntentType.RETIREMENTAGE;
            case "VoyaAccountActivityIntent":
                return VoyaIntentType.ACCOUNTACTIVITY;
            case "VoyaViewNotificationsIntent":
                return VoyaIntentType.VIEWNOTIFICATIONS;
            default:
                return VoyaIntentType.QUIT;
        }
    }
}
