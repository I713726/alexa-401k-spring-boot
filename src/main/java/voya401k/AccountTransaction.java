package voya401k;

import java.util.Date;

public class AccountTransaction {
    private Date date;
    private String description;

    AccountTransaction(Date date, String description) {
        this.date = date;
        this.description = description;
    }

    public Date getDate() {
        return  this.date;
    }

    public String getDescription() {
        return this.description;
    }
}
