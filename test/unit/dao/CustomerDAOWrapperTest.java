package unit.dao;

import dao.CustomerDAOWrapper;
import dao.DoctorDAOWrapper;
import dao.GenericDAO;
import models.Customer;
import models.Doctor;
import org.jongo.MongoCursor;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import play.test.FakeApplication;
import play.test.Helpers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static play.test.Helpers.fakeApplication;

/**
 * Created by HamedMahdavi on 1/4/2017.
 */
public class CustomerDAOWrapperTest {


    private Customer customerMock;
    private GenericDAO<Customer> daoMock;
    private CustomerDAOWrapper customerDAOWrapperTestInstance;
    private static FakeApplication fakeApp;

    @BeforeClass
    public static void initApp() {
        fakeApp = Helpers.fakeApplication();
        Helpers.start(fakeApplication());
    }

    @AfterClass
    public static void stopApp() {
        Helpers.stop(fakeApp);
    }

    @Before
    public void setupMocks() {

        //init mocks

        customerMock = mock(Customer.class);
        daoMock = mock(GenericDAO.class);

        //set usermock1
        when(customerMock.getUsername()).thenReturn("hmdmahdavi");

        //set  daoMock
        when(daoMock.findOneByFieldName("username",
                customerMock.getUsername())).thenReturn(customerMock);
        customerDAOWrapperTestInstance = new CustomerDAOWrapper(daoMock);
    }

    @Test
    public void testFindByUsername(){
        Customer result = customerDAOWrapperTestInstance.findByUsername(customerMock.getUsername());
        assertEquals(result.getUsername(), customerMock.getUsername());
    }
}
