package voya401k;

public interface VoyaRequest {
    /**
     * returns the question number, used for tracking where the user is in the flow of interaction.
     * @return
     */
    int getQuestionNo();

    /**
     * returns the user's PIN
     * @return
     */
    int getVoyaPIN();

    /**
     * Returns the type of request
     * @return
     */
    VoyaRequestType getRequestType();

    /**
     * Returns a string symbolically representing the language and country, e.g. en-US, es-ES
     * @return
     */
    String getLocale();

    /**
     * Returns the intent type, if the request is an intent request. Otherwise, returns null.
     * @return
     */
    VoyaIntentType getIntent();
}