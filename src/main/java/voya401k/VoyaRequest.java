package voya401k;

import java.util.Calendar;
import java.util.Date;

public interface VoyaRequest {
    /**
     * returns the question number, used for tracking where the user is in the flow of interaction.
     * Question Numbers:
     *    0 - PIN
     *    1 - Would you like to hear suggestions to help you retire sooner?
     *    2 - reccomends that you increase savings by 2%, yes/no
     *    3 - option to save 1% more a year from now
     * @return the question number
     */
    int getQuestionNo();

    /**
     * returns the user's PIN
     * @return user PIN
     */
    int getVoyaPIN();

    /**
     * Returns the type of request
     * @return type of request in the response, from enum VoyaRequestType
     */
    VoyaRequestType getRequestType();

    /**
     * Returns a string symbolically representing the language and country, e.g. en-US, es-ES
     * @return locale code from <a href="https://developer.amazon.com/docs/custom-skills/develop-skills-in-multiple-languages.html">here</a>
     */
    String getLocale();

    /**
     * Returns the intent type, if the request is an intent request. Otherwise, returns null.
     * @return intent type from enum VoyaIntentType
     */
    VoyaIntentType getIntent();

    /**
     * If the intent is to retrieve transactions in a date range, this method returns the start date, otherwise it
     * returns the current date.
     * @return the starting date for looking up recent transactions
     */
    Calendar getStartDate();

    /**
     * If the intent is to retrieve transactions in a date range, this method returns the end date, otherwise it returns
     * the current date.
     * @return the ending date for looking up recent transactions
     */
    Calendar getEndDate();

    /**
     * Returns an index to tell what notification we are on, starting at one, zero if uninitialized
     * @return
     */
    int getNotificationNumber();

    /**
     * Returns true if the device making the request supports visual display
     * @return
     */
    boolean displaySupported();
}