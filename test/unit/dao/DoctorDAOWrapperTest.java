package unit.dao;

import dao.DoctorDAOWrapper;
import dao.GenericDAO;
import models.Doctor;
import org.jongo.MongoCursor;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import play.test.FakeApplication;
import play.test.Helpers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static play.test.Helpers.fakeApplication;
import static org.junit.Assert.assertEquals;

/**
 * Created by HamedMahdavi on 1/4/2017.
 */
public class DoctorDAOWrapperTest {

    private MongoCursor<Doctor> cursorMock;
    private Doctor doctorMock;
    private GenericDAO<Doctor> daoMock;
    private DoctorDAOWrapper doctorDAOWrapperTestInstance;
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
        cursorMock = mock(MongoCursor.class);
        doctorMock = mock(Doctor.class);
        daoMock = mock(GenericDAO.class);

        //set usermock1
        when(doctorMock.getUsername()).thenReturn("hmdmahdavi");
        when(doctorMock.getAccepted()).thenReturn(true);
        when(doctorMock.getSpeciality()).thenReturn("dentist");


        //set cursorMock
        when(cursorMock.next()).thenReturn(doctorMock);

        //set  daoMock
        when(daoMock.findByFieldName("speciality",
                doctorMock.getSpeciality())).thenReturn(cursorMock);
        when(daoMock.findOneByFieldName("username",
                doctorMock.getUsername())).thenReturn(doctorMock);
        when(daoMock.findByFieldName("accepted",true)).thenReturn(cursorMock);

        doctorDAOWrapperTestInstance = new DoctorDAOWrapper(daoMock);
    }

    @Test
    public void testFindByAccepted(){
        Doctor result = doctorDAOWrapperTestInstance.findByAccepted(true).next();

        assertEquals(result.getAccepted(), doctorMock.getAccepted());
        assertEquals(result.getUsername(), doctorMock.getUsername());
    }

    @Test
    public void testFindByUsername(){
        Doctor result = doctorDAOWrapperTestInstance.findByUsername(doctorMock.getUsername());
        assertEquals(result.getUsername(), doctorMock.getUsername());
    }

    @Test
    public void testFindBySpeciality(){
        MongoCursor<Doctor> result = doctorDAOWrapperTestInstance.findBySpeciality(doctorMock.getSpeciality());
        assertEquals(result.next().getUsername(), doctorMock.getUsername());
    }


}
