package models;

import java.util.Date;

/**
 * Created by hamed on 1/8/17 AD.
 */
public class AppointmentInterval {
    private Date date;
    private String startTime; //format sample 16:00
    private String endTime; // //format sample 19:00

    public AppointmentInterval(Date date, String startTime, String endTime){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
