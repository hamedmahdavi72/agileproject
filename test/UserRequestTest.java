import com.fasterxml.jackson.databind.JsonNode;
import config.Messages;
import controllers.SessionIdPool;
import dao.CustomerDAOWrapper;
import forms.UserForm;
import forms.UserSignUpForm;
import models.Customer;
import models.JongoInstanceProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Application;
import play.libs.Json;
import play.mvc.Http;


import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;


import java.util.HashMap;

import static org.junit.Assert.*;
import static play.mvc.Http.RequestBuilder;
import static play.test.Helpers.*;

/**
 * Created by HamedMahdavi on 12/20/2016.
 */
public class UserRequestTest {
    static Application fakeApp;
    static Customer customer;

    @BeforeClass
    public static void prepareDatabase() {
        fakeApp = Helpers.fakeApplication();
        Helpers.start(fakeApplication());
        customer = new Customer();
        customer.setUsername("hmdmahdavi");
        customer.setPassword("123456789");
        customer.setEmail("hamedmahdavi72@gmail.com");
        CustomerDAOWrapper.getInstance().getCustomerDAO().save(customer);
    }

    @AfterClass
    public static void cleanUpDatabase() {
        CustomerDAOWrapper.getInstance().getCustomerDAO().remove(customer);
        Helpers.stop(fakeApp);
    }


    @Test
    public void testLoginInvalidUsername() {
        UserForm userForm = new UserForm("hamed", "123456789");
        JsonNode jsonBody = Json.toJson(userForm);
        RequestBuilder request = fakeRequest(POST, "/login/").bodyJson(jsonBody);
        Result result = route(request);
        JsonNode response = Json.parse(contentAsString(result));
        JsonNode expected = Messages.generateInvalidUsernameMessages().toJsonResponse();
        assertEquals(expected, response);

        boolean isLoggedIn = SessionIdPool.isLoggedIn(userForm.getUsername());
        assertEquals(false, isLoggedIn);


    }


    @Test
    public void testLoginWrongPassword() {
        UserForm userForm = new UserForm("hmdmahdavi", "123456");
        JsonNode jsonBody = Json.toJson(userForm);
        RequestBuilder request = fakeRequest(POST, "/login/").bodyJson(jsonBody);
        Result result = route(request);
        JsonNode response = Json.parse(contentAsString(result));
        JsonNode expected = Messages.generateWrongPasswordMessages().toJsonResponse();
        assertEquals(expected, response);


    }

    @Test
    public void testLoginSuccessfulLoginAndLogout() {
        UserForm userForm = new UserForm("hmdmahdavi", "123456789");
        JsonNode jsonBody = Json.toJson(userForm);
        RequestBuilder request = fakeRequest(POST, "/login/").bodyJson(jsonBody);
        Result result = route(request);
        JsonNode response = Json.parse(contentAsString(result));
        JsonNode expected = Messages.generateSuccessfulLoginMessages().toJsonResponse();
        assertEquals(expected, response);

        assertEquals(true, SessionIdPool.isLoggedIn(userForm.getUsername()));

        //logout
        request = fakeRequest(GET, "/logout");
        HashMap<String,String> sessionMap = new HashMap<>();
        sessionMap.put("sessionId",SessionIdPool.getSessionId(userForm.getUsername()));
        request.session(sessionMap);
        result = route(request);



        assertEquals(false, SessionIdPool.isLoggedIn(userForm.getUsername()));


    }

    @Test
    public void testLogoutUnauthorized(){
        RequestBuilder request = fakeRequest(GET, "/logout");
        Result result = route(request);
        assertEquals(null,result.contentType());
    }

    @Test
    public void testSignupTakenUsername(){
        UserSignUpForm signUpForm = new UserSignUpForm();
        signUpForm.setUsername("hmdmahdavi");
        signUpForm.setPassword("123456");
        signUpForm.setEmail("mahdavi@gmail.com");
        signUpForm.setFirstName("hamed");
        signUpForm.setLastName("mahdavi");
        signUpForm.setNationalId("0370975774");

        RequestBuilder request = fakeRequest(POST,"/signup/").bodyJson(Json.toJson(signUpForm));

        Result result = route(request);
        JsonNode response = Json.parse(contentAsString(result));
        JsonNode expected = Messages.generateTakenUsernameMessage().toJsonResponse();
        assertEquals(expected,response);
    }

    @Test
    public void testSignupSuccessful() throws InterruptedException {
        UserSignUpForm signUpForm = new UserSignUpForm();
        signUpForm.setUsername("motamed");
        signUpForm.setPassword("123456");
        signUpForm.setEmail("motamed@gmail.com");
        signUpForm.setFirstName("ariya");
        signUpForm.setLastName("motamed");
        signUpForm.setNationalId("03712312");


        RequestBuilder request = fakeRequest(POST,"/signup/").bodyJson(Json.toJson(signUpForm));
        route(request);
        Customer customer = CustomerDAOWrapper.getInstance().findByUsername(signUpForm.getUsername());
        assertNotNull(customer);
        CustomerDAOWrapper.getInstance().getCustomerDAO().remove(customer);

    }
}
