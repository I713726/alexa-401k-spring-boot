package voya401k;

import java.util.Calendar;
import java.util.Date;

public class VoyaRequestImpl implements VoyaRequest {

    int questionNo;
    int voyaPIN;
    VoyaRequestType requestType;
    String locale;
    VoyaIntentType intentType;
    Calendar startDate;
    Calendar endDate;

    public VoyaRequestImpl(int questionNo, int voyaPIN,
                           VoyaRequestType requestType, String locale, VoyaIntentType intentType, Calendar startDate, Calendar endDate){
        this.questionNo = questionNo;
        this.voyaPIN = voyaPIN;
        this.requestType = requestType;
        this.locale = locale;
        this.intentType = intentType;
        this.startDate = startDate;
        this.endDate = endDate;
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

    @Override
    public Calendar getStartDate() {
        return startDate;
    }

    @Override
    public Calendar getEndDate() {
        return endDate;
    }
}
