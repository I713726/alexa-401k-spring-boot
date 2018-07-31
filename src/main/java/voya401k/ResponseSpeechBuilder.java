package voya401k;

/**
 * This interface is part of an idea for creating varied responses each time a question is asked, to make the experience
 * more human. However, I am not sure if this is going to be the interface I ultimately end up using.
 */
public interface ResponseSpeechBuilder {
    public String getResponseSpeech(int questionNumber, VoyaRequestType requestType, VoyaIntentType intentType,
                                    String locale, VoyaUserDataObject userDataObject);
}
