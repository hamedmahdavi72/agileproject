package models;

import dao.AppointmentDAOWrapper;
import dao.CustomerDAOWrapper;
import dao.DoctorDAOWrapper;
import forms.AcceptAppointmentForm;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Created by HamedMahdavi on 12/13/2016.
 */
public class Appointment extends JongoModel {
    private String doctorUsername;
    private String customerUsername;
    private Date appointmentDate;

    public Appointment(){

    }

    public Appointment(String doctorUsername,String customerUsername, Date appointmentDate){
        this.doctorUsername = doctorUsername;
        this.customerUsername = customerUsername;
        this.appointmentDate = appointmentDate;
    }

    public Appointment(String doctorUsername, AcceptAppointmentForm acceptAppointmentForm) {
        this(doctorUsername,acceptAppointmentForm.getCustomerUsername(),new Date(acceptAppointmentForm.getDate()));
    }

    public static Appointment generateAndSaveAppointment(String doctorUsername,String customerUsername,Date date){
        Doctor doctor = DoctorDAOWrapper.getInstance().findByUsername(doctorUsername);
        Customer customer = CustomerDAOWrapper.getInstance().findByUsername(customerUsername);
        Appointment appointment = new Appointment(doctorUsername,customerUsername,date);
        AppointmentDAOWrapper.getInstance().getAppointmentDAO().save(appointment);
        return appointment;

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

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }
}
