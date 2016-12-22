package dao;

import models.Doctor;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import javax.print.Doc;
import javax.validation.constraints.AssertFalse;
import java.util.List;

/**
 * Created by ARYA on 12/18/2016.
 */
public class DoctorDAOWrapper {
    private GenericDAO<Doctor> doctorDAO = null;
    private static DoctorDAOWrapper instance = new DoctorDAOWrapper();
    private final String SEARCH_DOCTORS_QUERY = "{$and: " +
            "[{speciality : {$regex : #}},{geoLocation :{ $near :{ $geometry : " +
            "{type : \"Point\" ,coordinates : [#,#] },$maxDistance :#}}},{accepted: true}]}";

    private final String SEARCH_BY_SPECIALITY_QUERY = "{$and: [{speciality : {$regex : #}}, {accepted: true}]}";

    private final String SEARCH_BY_LOCATION = "{$and: [{geoLocation :{ $near :{ $geometry : " +
            "{type : \"Point\" ,coordinates : [#,#] },$maxDistance :#}}},{accepted: true}]}";

    //queries

    private DoctorDAOWrapper() {
        doctorDAO = new GenericDAO<>(Doctor.class);
    }

    public static DoctorDAOWrapper getInstance() {
        return instance;
    }

    public MongoCursor<Doctor> getBySpeciality(String specialityValue) {
        return doctorDAO.findByFieldName("speciality", specialityValue);
    }

    public GenericDAO<Doctor> getDoctorDAO() {
        return doctorDAO;
    }

    public Doctor findByUsername(String username) {
        return doctorDAO.findOneByFieldName("username", username);
    }

    public Iterable<Doctor> findByAccepted(boolean value) {
        Iterable<Doctor> doctors = doctorDAO.findByFieldName("accepted", value);
        return doctors;

    }

    public MongoCursor<Doctor> findNearDoctors(double latitude, double longitude, double maxDistance) {

        return doctorDAO.getCollection().find(SEARCH_BY_LOCATION, latitude, longitude, maxDistance).as(Doctor.class);
    }

    public MongoCursor<Doctor> searchDoctorsBySpecialityAndLocation(String speciality,
                                                                    double latitude, double longitude, double maxDistance) {
        return doctorDAO.getCollection().find(SEARCH_DOCTORS_QUERY,
                speciality, latitude, longitude, maxDistance).as(Doctor.class);
    }

    public MongoCursor<Doctor> findBySpeciality(String speciality) {
        return doctorDAO.getCollection().find(SEARCH_BY_SPECIALITY_QUERY, speciality).as(Doctor.class);
    }
}
