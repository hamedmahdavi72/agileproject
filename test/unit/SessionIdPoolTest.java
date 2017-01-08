package unit;

import controllers.SessionIdPool;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import play.test.FakeApplication;
import play.test.Helpers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by hamed on 1/6/17 AD.
 */
public class SessionIdPoolTest {

    HashMap<String,String> id2UserMapMock;
    HashMap<String,String> user2IdMapMock;
    SessionIdPool sessionIdPoolIns;
    Random generatorMock;
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
    public  void setUp(){
        id2UserMapMock = mock(HashMap.class);
        user2IdMapMock = mock(HashMap.class);
        generatorMock = mock(Random.class);

        SessionIdPool.setSessionIdToUsernameMap(id2UserMapMock);
        SessionIdPool.setUsernameToSessionIdMap(user2IdMapMock);
        SessionIdPool.setGenerator(generatorMock);


        when(id2UserMapMock.get("1994ala")).thenReturn("ala");
        when(id2UserMapMock.containsKey("1994ala")).thenReturn(true);
        when(user2IdMapMock.containsKey("ala")).thenReturn(true);
        when(user2IdMapMock.get("ala")).thenReturn("1994ala");
        when(generatorMock.nextInt(10000)).thenReturn(1994);

//        doReturn(id2UserMapMock.remove("1994ala")).when(id2UserMapMock).remove("1994ala");
//        when(id2UserMapMock.remove("1994ala")).
//        when(user2IdMapMock.remove("ala")).thenCallRealMethod();




    }

    @Test
    public void testIsSessionIdValid(){
        boolean result = sessionIdPoolIns.isSessionIdValid("1994ala");
        boolean result1 = sessionIdPoolIns.isSessionIdValid("94ala");
        assertEquals(result,true);
        assertEquals(result1,false);
    }

    @Test
    public void testisLoggedIn(){
        boolean result = sessionIdPoolIns.isLoggedIn("ala");
        boolean result1 = sessionIdPoolIns.isLoggedIn("someone");
        assertEquals(result,true);
        assertEquals(result1,false);
    }

    @Test
    public void testGenerateSessionId(){
        String result = sessionIdPoolIns.generateSessionId("ala");
        assertEquals(result,"1994ala");
    }

    @Test
    public void testAddUser(){
        String result = sessionIdPoolIns.addUser("ala");
        String result1 = sessionIdPoolIns.addUser("hamed");
        assertEquals(result,"1994ala");
        assertEquals(result1,"1994hamed");
    }

    @Test
    public void testRemoveUser(){

//        sessionIdPoolIns.addUser("ala");

//        System.out.println(id2UserMapMock.size());
//        when(id2UserMapMock.remove("1994ala")).thenCallRealMethod();
//        id2UserMapMock.remove("1994ala");
//        sessionIdPoolIns.removeUser("1994ala");
//        boolean result = sessionIdPoolIns.isLoggedIn("ala");
//        boolean result1 = sessionIdPoolIns.isSessionIdValid("1994ala");
//        System.out.println(result);
//        System.out.println(result1);
//        assertEquals(result,false);
//        assertEquals(result1,false);
    }


}
