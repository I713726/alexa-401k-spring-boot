package voya401k;

/**
 * This interface represents the response sent from the web application to the virtual assistant. How this is constructed
 * into JSON is another class's responsibility
 */
public interface VoyaResponse {

    /**
     * Returns the question number.
     * @return
     */
    int getQuestionNumber();

    /**
     * Returns the user's PIN.
     * @return
     */
    int getUserPIN();

    /**
     * Returns the speech to be said by the virtual assistant.
     * @return
     */
    String getSpeech();

    /**
     * Returns the text used for a reprompt.
     * TODO: What if we don't want a reprompt
     * @return
     */
    String getReprompt();

    /**
     * Returns the boolean that says wether the session ends. If true, this response ends the session, if false the
     * session continues.
     * @return
     */
    boolean getShouldSessionEnd();
}
