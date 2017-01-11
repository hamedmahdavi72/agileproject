package forms;

import models.AppointmentInterval;



import java.util.ArrayList;
import java.util.Date;

/**
 * Created by HamedMahdavi on 1/11/2017.
 */
public class AppointmentRequestForm {

    String doctorUsername;
    ArrayList<AppointmentIntervalForm> intervals;


    public void setDoctorUsername(String doctorUsername) {
        this.doctorUsername = doctorUsername;
    }

    public String getDoctorUsername() {
        return doctorUsername;
    }

    public void setIntervals(ArrayList<AppointmentIntervalForm> intervals) {
        this.intervals = intervals;
    }

    public ArrayList<AppointmentIntervalForm> getIntervals() {
        return intervals;
    }


    public ArrayList<AppointmentInterval> generateAppointmentIntervals(){
        ArrayList<AppointmentInterval> result = new ArrayList<>();
        intervals.forEach(interval -> result.add(interval.generateAppointmentInterval()));
        return result;
    }

}





