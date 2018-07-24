package voya401k;
/**
This interface represents all of the data for a user that we will be working with. It is analagous to the DataRow
variable in the javascript file.
 **/

public interface VoyaUserDataObject {
    //All of these values would have to be retrieved from the database
    String getFirstName();
    String getLastName();
    int getAccoutBalance();
    String getLastUpdatedDate();
    double getRateOfReturn();
    int getProjectedRetirementAge();
    double getSavingsRate();

    //These values could either be retrieved or calcualted
    double getSavingsRateIncrease();
    int getLoweredRetirementAge();

}
