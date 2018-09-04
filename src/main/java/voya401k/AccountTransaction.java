package voya401k;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AccountTransaction {
    private Calendar date;
    private String description;

    AccountTransaction(GregorianCalendar date, String description) {
        this.date = date;
        this.description = description;
    }

    public Calendar getDate() {
        return  this.date;
    }

    public String getDescription() {
        return this.description;
    }
}
