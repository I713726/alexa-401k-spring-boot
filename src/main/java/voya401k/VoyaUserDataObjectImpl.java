package voya401k;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VoyaUserDataObjectImpl implements VoyaUserDataObject {

    String firstName;
    String lastName;
    int accountBalance;
    String lastUpdated;
    double rateOfReturn;
    double savingsRate;
    List<AccountTransaction> transactions;

    public VoyaUserDataObjectImpl(String firstName, String lastName, int accountBalance, String lastUpdated,
                                  double rateOfReturn, double savingsRate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountBalance = accountBalance;
        this.rateOfReturn = rateOfReturn;
        this.lastUpdated = lastUpdated;
        this.savingsRate = savingsRate;
    }

    public void setTransactions(List<AccountTransaction> transactions) {
        this.transactions = transactions;
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

    @Override
    public List<String> getRecentTransactions(Date fromDate, Date toDate) {
        ArrayList<String> result = new ArrayList<>();
        for(AccountTransaction transaction: this.transactions) {
            //TODO: NEED TO MAKE RANGE INCLUSIVE
            if(!transaction.getDate().after(toDate) && !transaction.getDate().before(fromDate)) {
                SimpleDateFormat alexaFormat = new SimpleDateFormat("yyyy-MM-dd");
                System.out.println(transaction.getDescription() + " on " + alexaFormat.format(transaction.getDate()));
                result.add(transaction.getDescription() + " on " + alexaFormat.format(transaction.getDate()));
            }
        }
        return result;
    }
}
