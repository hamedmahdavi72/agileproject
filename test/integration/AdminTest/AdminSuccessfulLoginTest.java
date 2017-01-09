package integration.AdminTest;

import com.fasterxml.jackson.databind.JsonNode;
import config.Messages;
import controllers.SessionIdPool;
import dao.AdminDAOWrapper;
import forms.UserForm;
import models.Admin;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Application;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;
import static play.test.Helpers.route;

/**
 * Created by HamedMahdavi on 1/10/2017.
 */
public class AdminSuccessfulLoginTest {
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
    public void testSuccessfulAdminLoginAndLogout(){

        UserForm adminForm = new UserForm();
        adminForm.setPassword("sysadmin");
        adminForm.setUsername("sysadmin");
        JsonNode jsonBody = Json.toJson(adminForm);
        Http.RequestBuilder request = fakeRequest(POST,"/admin/").bodyJson(jsonBody);
        Result result = route(request);
        JsonNode response = Json.parse(contentAsString(result));
        JsonNode expected = Messages.generateSuccessfulAdminLoginMessage().toJsonResponse();
        assertEquals(expected, response);

        boolean isLoggedIn = SessionIdPool.isLoggedIn(adminForm.getUsername());
        assertEquals(true, isLoggedIn);

        //logout
        Http.RequestBuilder logoutRequest = fakeRequest(GET, "/logout");

        HashMap<String,String> sessionMap = new HashMap<>();
        sessionMap.put("sessionId",SessionIdPool.getSessionId(adminForm.getUsername()));
        logoutRequest.session(sessionMap);
        route(logoutRequest);


        assertEquals(false, SessionIdPool.isLoggedIn(adminForm.getUsername()));


    }
}
