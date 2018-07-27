package voya401k;

import java.util.List;
import java.util.Map;

public class ResponseSpeechBuilderImpl implements ResponseSpeechBuilder{
    Map<Integer, Map<VoyaIntentType, Map<String, List<String>>>> responseMap;

    public ResponseSpeechBuilderImpl() {
        this.initalizeResponses();
    }

    @Override
    public String getResponseSpeech(int questionNumber, VoyaRequestType requestType, VoyaIntentType intentType, String locale, VoyaUserDataObject userDataObject) {
        return null;
    }

    private void initalizeResponses() {

    }
}
