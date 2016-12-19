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
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Content;

/**
 * Created by ARYA on 12/17/2016.
 */
public class UserRequest extends Controller {

    public static Messages loginUserToSystem(User user,UserForm userForm){

        if(user != null && user.getPassword() == userForm.getPassword()){
            session().clear();
            session("username",user.getUsername());
             return generateSuccessfulLoginMessages();
        }
        else if(user != null && user.getPassword() != userForm.getPassword()){
            return generateWrongPasswordMessages();
        }
        else {
            return generateInvalidUsernameMessages();
        }

    }

    private static Messages generateInvalidUsernameMessages() {
        Messages msg;
        msg = new Messages(MessageConstants.getInstance().getUsernameField());
        msg.addMessagesToFieldName(MessageConstants.getInstance().getUsernameField(),
                MessageConstants.getInstance().getInvalidUsernameMessage());
        return msg;
    }

    private static Messages generateWrongPasswordMessages(){
        Messages msg;
        msg = new Messages(MessageConstants.getInstance().getPasswordField());
        msg.addMessagesToFieldName(MessageConstants.getInstance().getPasswordField(),
                MessageConstants.getInstance().getWrongPasswordMessage());
        return msg;
    }

    private static Messages generateSuccessfulLoginMessages() {
        Messages msg;
        msg = new Messages(MessageConstants.getInstance().getRedirectLoginPageField());
        msg.addMessagesToFieldName(MessageConstants.getInstance().getRedirectLoginPageField(),
                MessageConstants.getInstance().getRedirectMessage());
        return msg;
    }


    public static Result loginController() {

        JsonNode info;
        if(request().method().equalsIgnoreCase("post")){
            Form<UserForm> loginForm = Form.form(UserForm.class).bindFromRequest();
            User user = null;
            try {
                UserForm userForm = loginForm.get();
                if(isCustomer(userForm)){
                    user = CustomerDAOWrapper.getInstance().findByUsername(userForm.getUsername());
                }
                else if(isDoctor(userForm)){
                        user = DoctorDAOWrapper.getInstance().findByUsername(userForm.getUsername());
                    }


                return ok(loginUserToSystem(user,userForm).toJsonResponse());

            }
            catch (IllegalStateException e){
                System.out.println(loginForm.errorsAsJson());
                return ok(loginForm.errorsAsJson());
            }
        }
        Content html = views.html.user.login.render();
        return ok(html);
    }

    private static boolean isDoctor(UserForm userForm) {
        return DoctorDAOWrapper.getInstance().findByUsername(userForm.getUsername()) != null;
    }

    private static boolean isCustomer(UserForm userForm) {
        return CustomerDAOWrapper.getInstance().findByUsername(userForm.getUsername()) != null;
    }

    public static Result signUpController(){

        if(request().method().equalsIgnoreCase("post")){
            Form<UserSignUpForm> signUpForm = Form.form(UserSignUpForm.class).bindFromRequest();
            try{
                UserSignUpForm userSignUpForm = signUpForm.get();
                if(isUsernameTaken(userSignUpForm)){
                    Messages msg = generateTakenUsernameMessage();
                    return ok(msg.toJsonResponse());
                }
                else {
                    Customer customer = new Customer(userSignUpForm);
                    CustomerDAOWrapper.getInstance().getCustomerDAO().save(customer);
                }
            }
            catch (IllegalStateException e){
                    return ok(signUpForm.errorsAsJson());
            }
        }
        Content html = views.html.user.signup.render();
        return ok(html);
    }

    private static Messages generateTakenUsernameMessage() {
        Messages msg = new Messages(MessageConstants.getInstance().getUsernameField());
        msg.addMessagesToFieldName(MessageConstants.getInstance().getUsernameField(),
                MessageConstants.getInstance().getUsernameAlreadyTakenMessage());
        return msg;
    }

    private static boolean isUsernameTaken(UserSignUpForm userSignUpForm) {
        return isCustomer(userSignUpForm)||isDoctor(userSignUpForm);
    }


    public static Result doctorSignupController() {
        Content html = views.html.user.doctorSignup.render();
        return ok(html);
    }
}

