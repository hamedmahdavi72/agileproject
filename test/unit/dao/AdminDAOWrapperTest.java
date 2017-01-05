package unit.dao;

import dao.AdminDAOWrapper;
import dao.CustomerDAOWrapper;
import dao.GenericDAO;
import models.Admin;
import models.Customer;
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
public class AdminDAOWrapperTest {

    private Admin adminMock;
    private GenericDAO<Admin> daoMock;
    private AdminDAOWrapper adminDAOWrapperTestInstance;
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

        adminMock = mock(Admin.class);
        daoMock = mock(GenericDAO.class);

        //set usermock1
        when(adminMock.getUsername()).thenReturn("hmdmahdavi");

        //set  daoMock
        when(daoMock.findOneByFieldName("username",
                adminMock.getUsername())).thenReturn(adminMock);
        adminDAOWrapperTestInstance = new AdminDAOWrapper(daoMock);
    }

    @Test
    public void testFindByUsername(){
        Admin result = adminDAOWrapperTestInstance.findByUsername(adminMock.getUsername());
        assertEquals(result.getUsername(), adminMock.getUsername());
    }
}
