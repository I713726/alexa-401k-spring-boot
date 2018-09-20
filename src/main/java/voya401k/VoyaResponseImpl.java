package voya401k;

/**
 * This class is an implementation of the VoyaResponse interface.
 */
public class VoyaResponseImpl implements VoyaResponse {

    int questionNumber;
    int userPIN;
    String speech;
    String reprompt;
    boolean shouldSessionEnd;
    int notificationNumber;
    VisualDisplay display;

    public VoyaResponseImpl(int questionNumber, int userPIN, String speech, String reprompt, boolean shouldSessionEnd,
                            int notificationNumber) {
        this.questionNumber = questionNumber;
        this.userPIN = userPIN;
        this.speech = speech;
        this.reprompt = reprompt;
        this.shouldSessionEnd = shouldSessionEnd;
        this.notificationNumber = notificationNumber;
    }


    @Override
    public int getQuestionNumber() {
        return this.questionNumber;
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
        return this.shouldSessionEnd;
    }

    @Override
    public int getNotificationNumber() {
        return notificationNumber;
    }

    @Override
    public void setVisualDisplay(VisualDisplay visualDisplay) {
        this.display = visualDisplay;
    }

    @Override
    public VisualDisplay getVisualDisplay() {
        return this.display;
    }
}
