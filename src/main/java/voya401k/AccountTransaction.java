package voya401k;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AccountTransaction {
    private Calendar date;
    private String activity;
    private String fund;
    private float numUnits;
    private float unitPrice;
    private float amount;

    AccountTransaction(GregorianCalendar date, String activity, String fund,
                       float numUnits, float unitPrice, float amount) {
        this.date = date;
        this.activity = activity;
        this.fund = fund;
        this.numUnits = numUnits;
        this.unitPrice = unitPrice;
        this.amount = amount;
    }

    public Calendar getDate() {
        return  this.date;
    }

    public String getActivity() {
        return this.activity;
    }

    public String getFund() {
        return  this.fund;
    }

    public float getNumUnits() {
        return  numUnits;
    }

    public  float getUnitPrice() {
        return unitPrice;
    }

    public float getAmount() {
        return  amount;
    }
}
