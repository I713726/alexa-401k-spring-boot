package voya401k;
/**
This interface represents all of the data for a user that we will be working with. It is analagous to the DataRow
variable in the javascript file.
 **/

public interface VoyaUserDataObject {
    //All of these values would have to be retrieved from the database

    /**
     * Returns the first name of the user.
     * @return
     */
    String getFirstName();

    /**
     * Returns the last name of the user.
     * @return
     */
    String getLastName();

    /**
     * Returns the user's account balance
     * @return
     */
    int getAccountBalance();

    /**
     * Returns the date the account was last updated.
     * @return
     */
    String getLastUpdatedDate();

    /**
     * Returns the user's plan name
     * @return
     */
    String getPlanName();

    /**
     * returns the user's rate of return
     * @return
     */
    double getRateOfReturn();

    /**
     * Returns the user's projected age of retirement
     * @return
     */
    int getProjectedRetirementAge();

    /**
     * Return the user's current savings rate
     * @return
     */
    double getSavingsRate();

    //These values could either be retrieved or calcualted

    /**
     * Returns the recommended savings rate increase for this user.
     * @return
     */
    double getSavingsRateIncrease();

    /**
     * Returns the projected retirement age based on the reccomended savings rate increase
     * @return
     */
    int getLoweredRetirementAge();

}
