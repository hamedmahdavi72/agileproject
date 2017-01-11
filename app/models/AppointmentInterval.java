package models;

import java.util.Date;

/**
 * Created by hamed on 1/8/17 AD.
 */
public class AppointmentInterval {
    private Date date;
    private int fromHour; //format sample 16:00
    private int toHour; // //format sample 19:00

    public AppointmentInterval(Date date, int fromHour, int toHour){
        this.date = date;
        this.fromHour = fromHour;
        this.toHour = toHour;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setFromHour(int fromHour) {
        this.fromHour = fromHour;
    }

    public int getFromHour() {
        return fromHour;
    }

    public void setToHour(int toHour) {
        this.toHour = toHour;
    }

    public int getToHour() {
        return toHour;
    }
}
