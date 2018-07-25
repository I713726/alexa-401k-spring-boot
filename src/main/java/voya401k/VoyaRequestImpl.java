package voya401k;

public class VoyaRequestImpl implements VoyaRequest {

    int questionNo;
    int voyaPIN;
    VoyaRequestType requestType;
    String locale;
    VoyaIntentType intentType;

    public VoyaRequestImpl(int questionNo, int voyaPIN,
                           VoyaRequestType requestType, String locale, VoyaIntentType intentType){
        this.questionNo = questionNo;
        this.voyaPIN = voyaPIN;
        this.requestType = requestType;
        this.locale = locale;
        this.intentType = intentType;
    }

    @Override
    public int getQuestionNo() {
        return questionNo;
    }

    @Override
    public int getVoyaPIN() {
        return voyaPIN;
    }

    @Override
    public VoyaRequestType getRequestType() {
        return requestType;
    }

    @Override
    public String getLocale() {
        return locale;
    }

    @Override
    public VoyaIntentType getIntent() {
        return intentType;
    }
}
