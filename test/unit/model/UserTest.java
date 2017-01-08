package unit.model;

import dao.CustomerDAOWrapper;
import dao.DoctorDAOWrapper;
import dao.GenericDAO;
import junit.framework.Assert;
import models.Customer;
import models.Doctor;
import models.JongoModel;
import models.User;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import play.test.FakeApplication;
import play.test.Helpers;

import javax.print.Doc;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;




/**
 * Created by hamed on 1/5/17 AD.
 */
public class UserTest {

    CustomerDAOWrapper customerDaoMock;
    DoctorDAOWrapper doctorDaoMock;
    User userIns;
    Doctor doctorIns;
    Customer customerIns;
    GenericDAO<Doctor> doctorDaoIns;
    GenericDAO<Customer> customerDaoIns;
    MongoCollection collectionMock;
    private static FakeApplication fakeApp;

    @BeforeClass
    public static void initApp(){
        fakeApp = Helpers.fakeApplication();
        Helpers.start(fakeApp);
    }

    @AfterClass
    public static void stopApp(){
        Helpers.stop(fakeApp);
    }

    @Before
    public void setUp(){
        userIns = spy(User.class);
        customerDaoMock = mock(CustomerDAOWrapper.class);
        doctorDaoMock = mock(DoctorDAOWrapper.class);
        collectionMock = mock(MongoCollection.class, RETURNS_DEEP_STUBS);
        doctorIns = mock(Doctor.class);
        customerIns = mock(Customer.class);
        doctorDaoIns = spy(GenericDAO.class);
        doctorDaoIns.setType(Doctor.class);
        customerDaoIns = spy(GenericDAO.class);
        customerDaoIns.setType(Customer.class);

        when(collectionMock.findOne("{#: #}","firstName","ala").
                as(Doctor.class)).thenReturn(doctorIns);

        when(collectionMock.findOne("{#: #}","firstName","akbar").
                as(Customer.class)).thenReturn(customerIns);


    }

    @Test
    public void testIsDoctor(){
//        boolean result = userIns.isDoctor("ala");
//        boolean result1 = userIns.isDoctor("ahmad");
//        assertEquals(result,true);
//        assertEquals(result1,false);

    }

    @Test
    public void testIsCustomer(){

    }

    @Test
    public void testIsUsernameTake(){

    }

    @Test
    public void testGetUser(){

    }



//
//
//    @Test
//    public void sampleTest(){
//        when(doctorDaoMock.getDoctorDAO()).thenReturn(doctorDaoIns);
//        assertEquals(doctorDaoMock.getDoctorDAO(),doctorDaoIns);
//
//    }



}
