package models;

import dao.AppointmentDAOWrapper;
import dao.CustomerDAOWrapper;
import dao.DoctorDAOWrapper;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Created by HamedMahdavi on 12/13/2016.
 */
public class Appointment extends JongoModel {
    private String doctorUsername;
    private String customerUsername;
    private Date appointmentDate;

    public Appointment(String doctorUsername,String customerUsername, Date appointmentDate){
        this.doctorUsername = doctorUsername;
        this.customerUsername = customerUsername;
        this.appointmentDate = appointmentDate;
    }

    public static Appointment generateAndSaveAppointment(String doctorUsername,String customerUsername,Date date){
        Doctor doctor = DoctorDAOWrapper.getInstance().findByUsername(doctorUsername);
        Customer customer = CustomerDAOWrapper.getInstance().findByUsername(customerUsername);
        Appointment appointment = new Appointment(doctorUsername,customerUsername,date);
        AppointmentDAOWrapper.getInstance().getAppointmentDAO().save(appointment);
        return appointment;

    }

}
