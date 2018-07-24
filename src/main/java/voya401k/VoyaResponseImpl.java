package voya401k;

/**
 * This class is an implementation of the VoyaResponse interface.
 */
public class VoyaResponseImpl implements VoyaResponse {

    int questionNumber;
    int userPIN;
    String speech;
    String reprompt;
    boolean shouldSessionEnd

    public VoyaResponseImpl(int questionNumber, int userPIN, String speech, String reprompt, boolean shouldSessionEnd) {
        this.questionNumber = questionNumber;
        this.userPIN = userPIN;
        this.speech = speech;
        this.reprompt = reprompt;
        this.shouldSessionEnd = shouldSessionEnd;
    }


    @Override
    public int getQuestionNumber() {
        return this.questionNumber
    }

    @Override
    public int getUserPIN() {
        return this.userPIN;
    }

    @Override
    public String getSpeech() {
        return this.speech;
    }

    @Override
    public String getReprompt() {
        return this.reprompt;
    }

    @Override
    public boolean getShouldSessionEnd() {
        return this.shouldSessionEnd    ;
    }
}
