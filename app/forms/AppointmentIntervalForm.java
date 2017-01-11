package forms;

import models.AppointmentInterval;

import java.util.Date;

class AppointmentIntervalForm {
    long date;
    int fromHour;
    int toHour;

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