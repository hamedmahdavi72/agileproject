package forms;

import models.AppointmentInterval;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Created by HamedMahdavi on 1/11/2017.
 */
public class AppointmentRequestForm {

    private String doctorUsername;
    private ArrayList<AppointmentIntervalForm> intervals;

    public AppointmentRequestForm(){}

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
        return intervals
                .stream()
                .map(AppointmentIntervalForm::generateAppointmentInterval)
                .collect(Collectors.toCollection(ArrayList::new));

    }

}





