package forms;

import models.AppointmentInterval;

import java.util.Date;

public class AppointmentIntervalForm {
    private long date;
    private int fromHour;
    private int toHour;

    public AppointmentIntervalForm(){}

    public int getFromHour() {
        return fromHour;
    }

    public void setFromHour(int fromHour) {
        this.fromHour = fromHour;
    }

    public int getToHour() {
        return toHour;
    }

    public void setToHour(int toHour) {
        this.toHour = toHour;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public AppointmentInterval generateAppointmentInterval(){
        return  new AppointmentInterval(new Date(date),fromHour,toHour);
    }
}