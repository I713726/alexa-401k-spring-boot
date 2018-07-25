package voya401k;

public interface VoyaRequest {
    int getQuestionNo();
    int getVoyaPIN();
    VoyaRequestType getRequestType();
    String getLocale();
    VoyaIntentType getIntent();
}