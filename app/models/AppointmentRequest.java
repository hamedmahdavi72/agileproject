package models;

import org.joda.time.Interval;

/**
 * Created by HamedMahdavi on 1/7/2017.
 */
public class AppointmentRequest extends JongoModel{

    private String customerUsername;
    private String doctorUsername;
    private AppointmentInterval appointmentInterval;

    public AppointmentRequest(String customerUsername, String doctorUsername, AppointmentInterval appointmentInterval){
        this.customerUsername = customerUsername;
        this.doctorUsername = doctorUsername;
        this.appointmentInterval = appointmentInterval;
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

    public void setAppointmentInterval(AppointmentInterval appointmentInterval) {
        this.appointmentInterval = appointmentInterval;
    }

    public AppointmentInterval getAppointmentInterval() {
        return appointmentInterval;
    }
}
