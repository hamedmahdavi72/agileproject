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

    private AppointmentDAOWrapper(){
        this.appointmentDAO = new GenericDAO<>(Appointment.class);
    }

    public static AppointmentDAOWrapper getInstance(){
        return instance;
    }

    public Appointment findById(ObjectId id){
        return appointmentDAO.findOneByFieldName("_id",id.toString());
    }

    public MongoCursor<Appointment> getAppointmentsAfterSpecificDate(String doctorUsername, Date date){

        return null;
    }


    public GenericDAO<Appointment> getAppointmentDAO() {
        return appointmentDAO;
    }

    public void setAppointmentDAO(GenericDAO<Appointment> appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }


}
