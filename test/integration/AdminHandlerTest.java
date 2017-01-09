package integration;

import com.fasterxml.jackson.databind.JsonNode;
import config.Messages;
import controllers.SessionIdPool;
import dao.AdminDAOWrapper;
import dao.CustomerDAOWrapper;
import forms.UserForm;
import models.Admin;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Application;
import play.libs.Json;
import play.mvc.Result;
import play.test.Helpers;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.fakeApplication;
import static play.mvc.Http.RequestBuilder;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.*;
/**
 * Created by hamed on 12/21/16 AD.
 */
public class AdminHandlerTest {
    static Application fakeApp;
    static Admin admin;

    @BeforeClass
    public static void prepareDatabase() {
        fakeApp = Helpers.fakeApplication();
        Helpers.start(fakeApplication());
        admin = new Admin();
        admin.setUsername("sysadmin");
        admin.setPassword("sysadmin");
        AdminDAOWrapper.getInstance().getAdminDAO().save(admin);
    }

    @AfterClass
    public static void cleanUpDatabase(){
        AdminDAOWrapper.getInstance().getAdminDAO().remove(admin);
        Helpers.stop(fakeApp);
    }

    @Test
    public void testLoginInvalidUsername(){
        UserForm adminForm = new UserForm();
        adminForm.setPassword("sysadmin");
        adminForm.setUsername("safa");
        JsonNode jsonBody = Json.toJson(adminForm);
        RequestBuilder request = fakeRequest(POST,"/admin/").bodyJson(jsonBody);
        Result result = route(request);
        JsonNode response = Json.parse(contentAsString(result));
        JsonNode expected = Messages.generateInvalidUsernameMessages().toJsonResponse();
        assertEquals(expected, response);

        boolean isLoggedIn = SessionIdPool.isLoggedIn(adminForm.getUsername());
        assertEquals(false, isLoggedIn);
    }

    @Test
    public void testLoginInvalidPassword(){
        UserForm adminForm = new UserForm();
        adminForm.setPassword("afaeqafgh");
        adminForm.setUsername("sysadmin");
        JsonNode jsonBody = Json.toJson(adminForm);
        RequestBuilder request = fakeRequest(POST,"/admin/").bodyJson(jsonBody);
        Result result = route(request);
        JsonNode response = Json.parse(contentAsString(result));
        JsonNode expected = Messages.generateWrongPasswordMessages().toJsonResponse();
        assertEquals(expected, response);

        boolean isLoggedIn = SessionIdPool.isLoggedIn(adminForm.getUsername());
        assertEquals(false, isLoggedIn);
    }


    @Test
    public void testSuccessfulAdminLoginAndLogout(){
        
        UserForm adminForm = new UserForm();
        adminForm.setPassword("sysadmin");
        adminForm.setUsername("sysadmin");
        JsonNode jsonBody = Json.toJson(adminForm);
        RequestBuilder request = fakeRequest(POST,"/admin/").bodyJson(jsonBody);
        Result result = route(request);
        JsonNode response = Json.parse(contentAsString(result));
        JsonNode expected = Messages.generateSuccessfulAdminLoginMessage().toJsonResponse();
        assertEquals(expected, response);

//        boolean isLoggedIn = SessionIdPool.isLoggedIn(adminForm.getUsername());
//        assertEquals(true, isLoggedIn);

        //logout
        request = fakeRequest(GET, "/logout");
        HashMap<String,String> sessionMap = new HashMap<>();
        sessionMap.put("sessionId",SessionIdPool.getSessionId(adminForm.getUsername()));
        request.session(sessionMap);
        result = route(request);



        assertEquals(false, SessionIdPool.isLoggedIn(adminForm.getUsername()));


    }



}
