package dao;

import models.Appointment;
import org.jongo.MongoCursor;
import queryresult.AppointmentsData;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by HamedMahdavi on 1/7/2017.
 */
public class AppointmentDAOWrapper {
    private GenericDAO<Appointment> appointmentDAO = null;
    private static AppointmentDAOWrapper instance = new AppointmentDAOWrapper();
    private final String FIND_AFTER_SPECIFIC_DATE_QUERY = "{$and:[{appointmentDate: {$gte: #}},{doctorUsername: #}]}";

    private final String GET_CUSTOMER_DATA_QUERY ="{$group : {_id : {\"customerUsername\":\"$customerUsername\", " +
            "\"doctorUsername\": \"$doctorUsername\"}, appointmentsDate: {$push: \"$appointmentDate\"}} }";


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

    public ArrayList<AppointmentsData> getCustomersData(String doctorUsername) {
        ArrayList<AppointmentsData> result = new ArrayList<>();
        Iterable<AppointmentsData> allDoctorsAppointmentsData = appointmentDAO.getCollection().aggregate(GET_CUSTOMER_DATA_QUERY)
                .as(AppointmentsData.class);

        allDoctorsAppointmentsData.forEach(appointmentData -> {
            if(appointmentData.get_id().getDoctorUsername().equals(doctorUsername))
                result.add(appointmentData);
        });

        return result;

    }


    public GenericDAO<Appointment> getAppointmentDAO() {
        return appointmentDAO;
    }

    public void setAppointmentDAO(GenericDAO<Appointment> appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }
}

