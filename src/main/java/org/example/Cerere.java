package org.example;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Cerere {
    private Utilizator utilizator;
    private String text;
    private Date date;
    private int priority;   // 1 is low, 5 is urgent

    public Cerere(String text, Date date, int priority, Utilizator utilizator) {
        this.text = text;
        this.date = date;
        this.priority = priority;
        this.utilizator = utilizator;
    }

    public Date getDate() {
        return date;
    }

    public int getPriority() {
        return priority;
    }

    public String toString() {
        String dateFormat = "dd-MMM-yyy HH:mm:ss";
        return new SimpleDateFormat(dateFormat).format(this.date) + " - " + this.text;
    }

    public Utilizator getUtilizator() {
        return utilizator;
    }
}
