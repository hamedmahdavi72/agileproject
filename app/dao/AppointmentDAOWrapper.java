package dao;

import models.Appointment;
import org.bson.types.ObjectId;
import org.jongo.MongoCursor;

import java.util.Date;

/**
 * Created by HamedMahdavi on 1/7/2017.
 */
public class AppointmentDAOWrapper {
    private GenericDAO<Appointment> appointmentDAO = null;
    private static AppointmentDAOWrapper instance = new AppointmentDAOWrapper();
    private final String FIND_AFTER_SPECIFIC_DATE_QUERY = "{$and:[{appointmentDate: {$gte: #}},{doctorUsername: #}]}";


    private AppointmentDAOWrapper(){
        this.appointmentDAO = new GenericDAO<>(Appointment.class);
    }

    public static AppointmentDAOWrapper getInstance(){
        return instance;
    }



    public MongoCursor<Appointment> findByCustomerUsername(String username){
        return appointmentDAO.findByFieldName("customerUsername",username);
    }

    public MongoCursor<Appointment> findByDoctorUsername(String username){
        return appointmentDAO.findByFieldName("doctorUsername",username);
    }



    public MongoCursor<Appointment> getAppointmentsAfterSpecificDate(String doctorUsername, Date date){
        return appointmentDAO.getCollection().find(FIND_AFTER_SPECIFIC_DATE_QUERY,date,doctorUsername)
                .as(Appointment.class);
    }


    public GenericDAO<Appointment> getAppointmentDAO() {
        return appointmentDAO;
    }

    public void setAppointmentDAO(GenericDAO<Appointment> appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }


}
