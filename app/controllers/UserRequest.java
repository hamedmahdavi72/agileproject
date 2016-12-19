package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import config.MessageConstants;
import config.Messages;
import dao.CustomerDAOWrapper;
import dao.DoctorDAOWrapper;
import forms.UserForm;
import forms.UserSignUpForm;
import models.Customer;
import models.User;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Content;

/**
 * Created by ARYA on 12/17/2016.
 */
public class UserRequest extends Controller {

    public static Result login() {

        JsonNode info;

        if(request().method().equalsIgnoreCase("post")){
            Form<UserForm> loginForm = Form.form(UserForm.class).bindFromRequest();
            User user = null;
            try {
                UserForm userForm = loginForm.get();
                if(CustomerDAOWrapper.getInstance().findByUsername(userForm.getUsername()) != null){
                    user = CustomerDAOWrapper.getInstance().findByUsername(userForm.getUsername());
                }
                else if(DoctorDAOWrapper.getInstance().findByUsername(userForm.getUsername()) != null){
                        user = DoctorDAOWrapper.getInstance().findByUsername(userForm.getUsername());
                    }
                if(user != null){
                    session().clear();
                    session("username",userForm.getUsername());
                    Messages msg = new Messages(MessageConstants.getInstance().getRedirectLoginPageField());
                    msg.addMessagesToFieldName(MessageConstants.getInstance().getRedirectLoginPageField(), MessageConstants.getInstance().getRedirectMessage());
                    return ok(msg.toJsonResponse());
                }
                else {
                    Messages msg = new Messages(MessageConstants.getInstance().getUsernameField());
                    msg.addMessagesToFieldName(MessageConstants.getInstance().getUsernameField(), MessageConstants.getInstance().getInvalidUsernameMessage());
                    return ok(Json.toJson(msg.toJsonResponse()));
                }

            }
            catch (IllegalStateException e){
                System.out.println(loginForm.errorsAsJson());
                return ok(loginForm.errorsAsJson());
            }
        }
        Content html = views.html.user.login.render();
        return ok(html);
    }

    public static Result signUp(){

        if(request().method().equalsIgnoreCase("post")){
            Form<UserSignUpForm> signUpForm = Form.form(UserSignUpForm.class).bindFromRequest();
            try{
                UserSignUpForm userSignUpForm = signUpForm.get();
                Customer customer = new Customer(userSignUpForm);
                if(CustomerDAOWrapper.getInstance().findByUsername(userSignUpForm.getUsername()) != null){
                   Messages msg = new Messages(MessageConstants.getInstance().getUsernameField());
                   msg.addMessagesToFieldName(MessageConstants.getInstance().getUsernameField(), MessageConstants.getInstance().getUsernameAlreadyTakenMessage());
                    return ok(msg.toJsonResponse());
                }
                CustomerDAOWrapper.getInstance().getCustomerDAO().save(customer);
            }
            catch (IllegalStateException e){
                    return ok(signUpForm.errorsAsJson());
            }
        }
        Content html = views.html.user.signup.render();
        return ok(html);
    }


    public static Result doctorSignup() {
        Content html = views.html.user.doctorSignup.render();
        return ok(html);
    }
}

