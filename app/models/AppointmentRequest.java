package models;

import forms.AppointmentRequestForm;
import org.bson.types.ObjectId;

import java.util.ArrayList;

/**
 * Created by HamedMahdavi on 1/7/2017.
 */
public class AppointmentRequest extends JongoModel{

    private String customerUsername;
    private String doctorUsername;
    private ArrayList<AppointmentInterval> appointmentInterval;
    private boolean answered;

    public AppointmentRequest(){}

    public AppointmentRequest(AppointmentRequestForm form,String customerUsername){
        this.customerUsername = customerUsername;
        this.doctorUsername = form.getDoctorUsername();
        this.appointmentInterval = form.generateAppointmentIntervals();
        this.answered = false;
    }

    public AppointmentRequest(String customerUsername, String doctorUsername, AppointmentInterval appInt) {
        this.id = new ObjectId();
        this.customerUsername = customerUsername;
        this.doctorUsername = doctorUsername;
        this.appointmentInterval = new ArrayList<>();
        appointmentInterval.add(appInt);
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
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }
}
