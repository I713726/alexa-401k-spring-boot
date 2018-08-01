package voya401k;
/**
This interface represents all of the data for a user that we will be working with. It is analagous to the DataRow
variable in the javascript file.
 **/

public interface VoyaUserDataObject {
    /**
     * Returns the first name of the user.
     * @return first name of the user
     */
    String getFirstName();

    /**
     * Returns the last name of the user.
     * @return last name of the user
     */
    String getLastName();

    /**
     * Returns the user's account balance
     * @return user's account balance
     */
    int getAccountBalance();

    /**
     * Returns the date the account was last updated.
     * @return date account was last updated
     */
    String getLastUpdatedDate();

    /**
     * Returns the user's plan name
     * @return user's plan name
     */
    String getPlanName();

    /**
     * returns the user's rate of return
     * @return user's rate of return
     */
    double getRateOfReturn();

    /**
     * Returns the user's projected age of retirement
     * @return projected age of retirement
     */
    int getProjectedRetirementAge();

    /**
     * Return the user's current savings rate
     * @return current savings rate
     */
    double getSavingsRate();

    /**
     * Returns the recommended savings rate increase for this user.
     * @return recommended savings rate increase. Could be calculated or retrieved.
     */
    double getSavingsRateIncrease();

    /**
     * Returns the projected retirement age based on the recommended savings rate increase
     * @return projected retirement age after recommended savings rate applied. Could be calculated or retrieved.
     */
    int getLoweredRetirementAge();

}
