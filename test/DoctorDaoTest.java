import dao.CustomerDAOWrapper;
import dao.DoctorDAOWrapper;
import forms.DoctorSignUpForm;
import models.Customer;
import models.Doctor;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import play.Application;
import play.test.Helpers;

import javax.print.Doc;
import java.util.ArrayList;

import static play.test.Helpers.fakeApplication;

/**
 * Created by HamedMahdavi on 12/21/2016.
 */
public class DoctorDaoTest {
    static Application fakeApp;
    static Doctor doctor;

    @BeforeClass
    public static void prepareDatabase() {
        fakeApp = Helpers.fakeApplication();
        Helpers.start(fakeApplication());
        DoctorSignUpForm signUpForm = new DoctorSignUpForm();
        signUpForm.setLastName("mahdavi");
        signUpForm.setSpeciality("دندان پزشکی ارتودنسی");
        doctor = new Doctor(signUpForm);
        doctor.getGeoLocation().setCoordinates(new double[]{40,70});
        doctor.setAccepted(true);
        DoctorDAOWrapper.getInstance().getDoctorDAO().save(doctor);
        DoctorDAOWrapper.getInstance().getDoctorDAO().getCollection().ensureIndex("{geoLocation : \"2dsphere\"}");

    }



    @AfterClass
    public static void cleanDatabase(){
        DoctorDAOWrapper.getInstance().getDoctorDAO().remove(doctor);
        Helpers.stop(fakeApp);
    }

    @Test
    public  void testFindNearDoctors(){
        Iterable<Doctor> doctors = DoctorDAOWrapper.getInstance().findNearDoctors(40.001,70.002,1000);

        for (Doctor foundDoctor: doctors) {
            if(foundDoctor.getId().equals(doctor.getId()))
                assertTrue(true);
                return;
        }
        assertTrue(false);



    }
    @Test
    public  void testSearchdoctors(){
        Iterable<Doctor> doctors = DoctorDAOWrapper.getInstance().
                searchDoctorsBySpecialityAndLocation("دندان پزشکی",40.001,70.002,1000);

        for (Doctor foundDoctor: doctors) {
            if(foundDoctor.getId().equals(doctor.getId()))
                assertTrue(true);
            return;
        }
        assertTrue(false);



    }

    @Test
    public  void testFindBySpeciality(){
        Iterable<Doctor> doctors = DoctorDAOWrapper.getInstance().
                findBySpeciality("دندان پزشکی");

        for (Doctor foundDoctor: doctors) {
            if(foundDoctor.getId().equals(doctor.getId()))
                assertTrue(true);
            return;
        }
        assertTrue(false);



    }
}
