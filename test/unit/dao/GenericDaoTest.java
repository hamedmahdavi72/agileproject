package unit.dao;

import dao.GenericDAO;
import models.Doctor;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import play.test.FakeApplication;
import play.test.Helpers;
import uk.co.panaxiom.playjongo.PlayJongo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static play.test.Helpers.fakeApplication;


/**
 * Created by HamedMahdavi on 1/4/2017.
 */

public class GenericDaoTest {

    PlayJongo jongoMock;
    MongoCollection collectionMock;
    MongoCursor<Doctor> cursor;
    Doctor userMock1;
    Doctor userMock2;
    GenericDAO<Doctor> daoTestInstance;
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

        jongoMock = mock(PlayJongo.class);
        collectionMock = mock(MongoCollection.class, RETURNS_DEEP_STUBS);
        cursor = mock(MongoCursor.class);
        userMock1 = mock(Doctor.class);
        userMock2 = mock(Doctor.class);
        daoTestInstance = spy(GenericDAO.class);
        daoTestInstance.setType(Doctor.class);


        //setup userMocks
        setupUserMocks(userMock1, "Hamed", "Mahdavi", "hmdmahdavi");
        setupUserMocks(userMock2, "Hamed", "Hoseini", "hmdhoseini");


        when(cursor.next()).thenReturn(userMock1).thenReturn(userMock2);


        when(collectionMock.find("{#: #}", "firstName", "Hamed").
                as(Doctor.class)).thenReturn(cursor);

        when(collectionMock.findOne("{#: #}", "firstName", "Hamed").
                as(Doctor.class)).thenReturn(userMock1);

        when(collectionMock.find("{#: #}", "accepted", false).
                as(Doctor.class)).thenReturn(cursor);

        when(daoTestInstance.getCollection()).thenReturn(collectionMock);


    }

    private void setupUserMocks(Doctor userMock, String firstName, String lastName, String username) {
        when(userMock.getFirstName()).thenReturn(firstName);
        when(userMock.getLastName()).thenReturn(lastName);
        when(userMock.getUsername()).thenReturn(username);
    }

    private void verifyResult(MongoCursor<Doctor> result) {
        assertEquals(result.next().getUsername(), userMock1.getUsername());
        assertEquals(result.next().getUsername(), userMock2.getUsername());
    }




    @Test
    public void testFindByFieldName() {
        MongoCursor<Doctor> result = daoTestInstance.findByFieldName("firstName", "Hamed");
        verifyResult(result);

    }


    @Test
    public void testFindByFieldNameBoolean(){
        MongoCursor<Doctor> result = daoTestInstance.findByFieldName("accepted",false);
        verifyResult(result);

    }

    @Test
    public void testFindOneByFieldName(){
        Doctor result = daoTestInstance.findOneByFieldName("firstName","Hamed");
        assertEquals(result.getUsername(),userMock1.getUsername());
    }


}
