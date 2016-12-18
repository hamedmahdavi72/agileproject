package dao;

import models.Doctor;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import javax.print.Doc;

/**
 * Created by ARYA on 12/18/2016.
 */
public class DoctorDAOWrapper {
    private GenericDAO<Doctor> doctorDAO = null;
    private static DoctorDAOWrapper instance = new DoctorDAOWrapper();

    //queries

    private DoctorDAOWrapper(){
        doctorDAO = new GenericDAO<>(Doctor.class);
    }

    public static DoctorDAOWrapper getInstance(){
        return instance;
    }
    public MongoCursor<Doctor> getBySpeciality(String specialityValue){
        return doctorDAO.findByFieldName("speciality",specialityValue);
    }

    public Doctor findByUsername(String username){
        return doctorDAO.findOneByFieldName("_id", username);
    }
}
