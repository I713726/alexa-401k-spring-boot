package voya401k;

import javax.print.DocFlavor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    List<VoyaNotification> notifications;

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

    public void setNotifications(List<VoyaNotification> notifications) {
        this.notifications = notifications;
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
    public List<String> getRecentTransactions(Calendar fromDate, Calendar toDate) {
        ArrayList<String> result = new ArrayList<>();
        for(AccountTransaction transaction: this.transactions) {
            //TODO: NEED TO MAKE RANGE INCLUSIVE
            if(!transaction.getDate().before(fromDate) && !transaction.getDate().after(toDate)) {
                SimpleDateFormat alexaFormat = new SimpleDateFormat("yyyy-MM-dd");
                System.out.println(transaction.getDescription() + " on " + alexaFormat.format(transaction.getDate().getTime()));
                result.add(transaction.getDescription() + " on " + alexaFormat.format(transaction.getDate().getTime()));
            }
        }
        return result;
    }

    @Override
    public List<VoyaNotification> getNotifications() {
        return new ArrayList<>(this.notifications);
    }
}
