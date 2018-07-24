package voya401k;

public interface VoyaRequest {
    int getQuestionNo();
    int getVoyaPin();
    VoyaRequestType getRequestType();
    String getLocale();
    VoyaIntentType getIntent();
}