package voya401k;

public class VoyaUserDataObjectImpl implements VoyaUserDataObject {

    String firstName;
    String lastName;
    int accountBalance;
    String lastUpdated;
    double rateOfReturn;
    double savingsRate;

    public VoyaUserDataObjectImpl(String firstName, String lastName, int accountBalance, String lastUpdated,
                                  double rateOfReturn, double savingsRate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountBalance = accountBalance;
        this.rateOfReturn = rateOfReturn;
        this.lastUpdated = lastUpdated;
        this.savingsRate = savingsRate;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public int getAccountBalance() {
        return accountBalance;
    }

    @Override
    public String getLastUpdatedDate() {
        return lastUpdated;
    }

    @Override
    public String getPlanName() {
        return "401K";
    }

    @Override
    public double getRateOfReturn() {
        return rateOfReturn;
    }

    @Override
    public int getProjectedRetirementAge() {
        return 67;
    }

    @Override
    public double getSavingsRate() {
        return savingsRate;
    }

    @Override
    public double getSavingsRateIncrease() {
        return 0.02;
    }

    @Override
    public int getLoweredRetirementAge() {
        return 65;
    }
}
