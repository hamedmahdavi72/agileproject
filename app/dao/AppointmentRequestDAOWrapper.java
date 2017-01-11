package dao;

import models.AppointmentRequest;
import org.bson.types.ObjectId;
import org.jongo.MongoCursor;

/**
 * Created by hamed on 1/8/17 AD.
 */
public class AppointmentRequestDAOWrapper {
    private GenericDAO<AppointmentRequest> appointmentRequestDAO = null;
    private static AppointmentRequestDAOWrapper instance = new AppointmentRequestDAOWrapper();

    private AppointmentRequestDAOWrapper(){
        this.appointmentRequestDAO = new GenericDAO<>(AppointmentRequest.class);
    }

    public static AppointmentRequestDAOWrapper getInstance() {
        return instance;
    }

    public MongoCursor<AppointmentRequest> findByDoctorUsername(String doctorUsername){
        return appointmentRequestDAO.findByFieldName("doctorUsername",doctorUsername);
    }

    public void setAppointmentRequestDAO(GenericDAO<AppointmentRequest> appointmentRequestDAO) {
        this.appointmentRequestDAO = appointmentRequestDAO;
    }

    public GenericDAO<AppointmentRequest> getAppointmentRequestDAO() {
        return appointmentRequestDAO;
    }
}
