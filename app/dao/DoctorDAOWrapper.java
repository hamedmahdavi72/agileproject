package dao;

import forms.SearchForm;
import models.Doctor;
import org.jongo.MongoCursor;



/**
 * Created by ARYA on 12/18/2016.
 */
public class DoctorDAOWrapper {
    private GenericDAO<Doctor> doctorDAO = null;
    private static DoctorDAOWrapper instance = new DoctorDAOWrapper();
    private final String SEARCH_DOCTORS_QUERY = "{$and:[{firstName : {$regex : #}},{lastName : {$regex : #}}," +
            "{speciality : {$regex : #}}," +
            "{geoLocation :{ $near :{ $geometry :{type : \"Point\" ,coordinates : [#,#] },$maxDistance :#}}}," +
            "{accepted: true}]}";

    private final double MAX_DISTANCE = 3700;

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



    public MongoCursor<Doctor> search(SearchForm searchForm) {
        return doctorDAO.getCollection().find(SEARCH_DOCTORS_QUERY,
                searchForm.getFirstName(),searchForm.getLastName(),searchForm.getSpeciality(),
                searchForm.findLatitude(),searchForm.findLongitude(),MAX_DISTANCE
                ).as(Doctor.class);
    }

}
