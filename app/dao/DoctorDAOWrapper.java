package dao;

import forms.SearchForm;

import models.Doctor;
import org.jongo.MongoCursor;
import play.api.libs.json.Json;
import queryresult.AppointmentsData;

import java.util.ArrayList;


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

    //just use it for testing
    public DoctorDAOWrapper(GenericDAO<Doctor> doctorDAO){
        this.doctorDAO = doctorDAO;
    }



    public static DoctorDAOWrapper getInstance() {
        return instance;
    }

    public MongoCursor<Doctor> findBySpeciality(String specialityValue) {
        return doctorDAO.findByFieldName("speciality", specialityValue);
    }

    public GenericDAO<Doctor> getDoctorDAO() {
        return doctorDAO;
    }

    public Doctor findByUsername(String username) {
        return doctorDAO.findOneByFieldName("username", username);
    }

    public MongoCursor<Doctor> findByAccepted(boolean value) {
        MongoCursor<Doctor> doctors = doctorDAO.findByFieldName("accepted", value);
        return doctors;

    }







    public MongoCursor<Doctor> search(SearchForm searchForm) {
        return doctorDAO.getCollection().find(SEARCH_DOCTORS_QUERY,
                searchForm.getFirstName(),searchForm.getLastName(),searchForm.getSpeciality(),
                searchForm.findLatitude(),searchForm.findLongitude(),MAX_DISTANCE
                ).as(Doctor.class);
    }

}
