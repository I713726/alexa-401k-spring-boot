package voya401k;

/**
 * This interface represents the response sent from the web application to the virtual assistant. How this is constructed
 * into JSON is another class's responsibility
 */
public interface VoyaResponse {

    /**
     * Returns the question number.
     *     0 - PIN
     *     1 - Would you like to hear suggestions to help you retire sooner?
     *     2 - reccomends that you increase savings by 2%, yes/no
     *     3 - option to save 1% more a year from now
     * @return the question number
     */
    int getQuestionNumber();

    /**
     * Returns the user's PIN.
     * @return user PIN
     */
    int getUserPIN();

    /**
     * Returns the speech to be said by the virtual assistant.
     * @return literal words to be said by the virtual assistant, no ssml tags
     */
    String getSpeech();

    /**
     * Returns the text used for a reprompt.
     * TODO: What if we don't want a reprompt
     * @return text used for reprompt (this exists in Amazon Alexa, I don't know if it's a thing in Google Home
     */
    String getReprompt();

    /**
     * Returns the boolean that says whether the session ends. If true, this response ends the session, if false the
     * session continues.
     * @return the boolean that says whether the session ends now.
     */
    boolean getShouldSessionEnd();

    /**
     * Number to tell us what notification we are on, starting at one, zero is uninitialized
     * @return
     */
    int getNotificationNumber();

    /**
     * gives the response a visual display
     */
    void setVisualDisplay(VisualDisplay visualDisplay);

    /**
     * returns the visual display object, containing visual info for this response. If there is no visual
     * display, this will return null
     * TODO: maybe return something other than Null
     * @return
     */
    VisualDisplay getVisualDisplay();
}
