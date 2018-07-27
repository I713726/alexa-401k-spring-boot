package voya401k;

public interface ResponseSpeechBuilder {
    public String getResponseSpeech(int questionNumber, VoyaRequestType requestType, VoyaIntentType intentType,
                                    String locale, VoyaUserDataObject userDataObject);
}
