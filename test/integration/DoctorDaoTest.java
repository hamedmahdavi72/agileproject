package integration;

import dao.DoctorDAOWrapper;
import forms.DoctorSignUpForm;
import forms.SearchForm;
import models.Doctor;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import play.Application;
import play.test.Helpers;

import static play.test.Helpers.fakeApplication;

/**
 * Created by HamedMahdavi on 12/21/2016.
 */
public class DoctorDaoTest {
    static Application fakeApp;
    static Doctor doctor;
    static SearchForm searchForm;

    @BeforeClass
    public static void prepareDatabase() {
        fakeApp = Helpers.fakeApplication();
        Helpers.start(fakeApplication());
        DoctorSignUpForm signUpForm = new DoctorSignUpForm();
        signUpForm.setFirstName("hamed");
        signUpForm.setLastName("mahdavi");
        signUpForm.setSpeciality("دندان پزشکی ارتودنسی");
        doctor = new Doctor(signUpForm);
        doctor.getGeoLocation().setCoordinates(new double[]{35.7681,51.448629});
        doctor.setAccepted(true);
        DoctorDAOWrapper.getInstance().getDoctorDAO().save(doctor);

    }

    @Before
    public  void beforeTest(){
            searchForm = new SearchForm();
            searchForm.setFirstName("");
            searchForm.setLastName("mahdavi");
            searchForm.setSpeciality("ارتودنسی");

            searchForm.setAreaName("1");

    }



    @AfterClass
    public static void cleanDatabase(){
        DoctorDAOWrapper.getInstance().getDoctorDAO().remove(doctor);
        Helpers.stop(fakeApp);
    }






    @Test
    public  void testSearchdoctors(){
        Iterable<Doctor> doctors = DoctorDAOWrapper.getInstance().
                search(searchForm);

        for (Doctor foundDoctor: doctors) {
            if(foundDoctor.getId().equals(doctor.getId()))
                assertTrue(true);
            return;
        }
        assertTrue(false);



    }


}
