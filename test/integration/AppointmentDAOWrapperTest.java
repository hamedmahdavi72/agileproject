package integration;


import dao.AppointmentDAOWrapper;
import dao.DoctorDAOWrapper;
import forms.DoctorSignUpForm;
import forms.SearchForm;
import models.Appointment;
import models.Doctor;
import org.apache.commons.lang3.time.DateUtils;
import org.jongo.MongoCursor;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Application;
import play.test.Helpers;
import static org.junit.Assert.*;
import java.util.Date;


/**
 * Created by HamedMahdavi on 1/7/2017.
 */
public class AppointmentDAOWrapperTest {

    static Application fakeApp;
    static Appointment appointment1;
    static Appointment appointment2;
    @BeforeClass
    public static void initApp() {
        fakeApp = Helpers.fakeApplication();
        Helpers.start(fakeApp);

        appointment1 = generateAppointment("mahdavi","hashemi");
        appointment2 = generateAppointment("mahdavi","emrani");
    }


    private static Appointment generateAppointment(String doctorUsername,String customerUsername) {
        Appointment appointment = new Appointment(doctorUsername,customerUsername,new Date());
        AppointmentDAOWrapper.getInstance().getAppointmentDAO().save(appointment);
        return appointment;
    }

    @AfterClass
    public static void stopApp(){
        AppointmentDAOWrapper.getInstance().getAppointmentDAO().remove(appointment1);
        AppointmentDAOWrapper.getInstance().getAppointmentDAO().remove(appointment2);
        Helpers.stop(fakeApp);
    }




    @Test
    public void testFindByCustomerUsername(){
        MongoCursor<Appointment> result = AppointmentDAOWrapper.getInstance().findByCustomerUsername("hashemi");
        assertEquals(1,result.count());
        result.forEach(appointment -> assertEquals("hashemi",appointment.getCustomerUsername()));

    }

    @Test
    public void testFindByDoctorUsername(){
        MongoCursor<Appointment> result = AppointmentDAOWrapper.getInstance().findByDoctorUsername("mahdavi");
        assertEquals(2,result.count());
        result.forEach(appointment -> assertEquals("mahdavi",appointment.getDoctorUsername()));
    }

    @Test
    public void testFindAfterSpecificDate(){
        MongoCursor<Appointment> result = AppointmentDAOWrapper.getInstance().
                getAppointmentsAfterSpecificDate("mahdavi",DateUtils.addDays(new Date(),-1));
        assertEquals(2,result.count());
        result.forEach(appointment -> assertEquals("mahdavi",appointment.getDoctorUsername()));
    }





}
