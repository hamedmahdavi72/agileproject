package models;

import forms.AppointmentRequestForm;
import org.joda.time.Interval;

import java.util.ArrayList;

/**
 * Created by HamedMahdavi on 1/7/2017.
 */
public class AppointmentRequest extends JongoModel{

    private String customerUsername;
    private String doctorUsername;
    private ArrayList<AppointmentInterval> appointmentInterval;
    private boolean isAnswered;

    public AppointmentRequest(){}

    public AppointmentRequest(AppointmentRequestForm form,String customerUsername){
        this.customerUsername = customerUsername;
        this.doctorUsername = form.getDoctorUsername();
        this.appointmentInterval = form.generateAppointmentIntervals();
        this.isAnswered = false;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setDoctorUsername(String doctorUsername) {
        this.doctorUsername = doctorUsername;
    }

    public String getDoctorUsername() {
        return doctorUsername;
    }

    public void setAppointmentInterval(ArrayList<AppointmentInterval> appointmentInterval) {
        this.appointmentInterval = appointmentInterval;
    }

    public ArrayList<AppointmentInterval> getAppointmentInterval() {
        return appointmentInterval;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }
}
