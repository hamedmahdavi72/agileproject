package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import config.Messages;
import dao.CustomerDAOWrapper;
import dao.DoctorDAOWrapper;
import forms.DoctorSignUpForm;
import forms.UserForm;
import forms.UserSignUpForm;
import models.Admin;
import models.Customer;
import models.Doctor;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.twirl.api.Content;

/**
 * Created by ARYA on 12/17/2016.
 */
public class UserRequest extends Controller {

    public static Messages loginUserToSystem(User user, UserForm userForm) {

        if (user != null && user.getPassword().equals(userForm.getPassword())) {

            session().clear();
            session("sessionId", SessionIdPool.addUser(user.getUsername()));
            return Messages.generateSuccessfulLoginMessages();

        } else if (user != null && !user.getPassword().equals(userForm.getPassword())) {

            return Messages.generateWrongPasswordMessages();
        } else {

            return Messages.generateInvalidUsernameMessages();
        }

    }

    public static Messages loginUserToSystem(Admin admin, UserForm userForm){
        if(admin != null && admin.getPassword().equals(userForm.getPassword())){
            session().clear();
            session("sessionId", SessionIdPool.addUser(admin.getUsername()));
            return Messages.generateSuccessfulAdminLoginMessage();
        } else if (admin != null && !admin.getPassword().equals(userForm.getPassword())){

            return Messages.generateWrongPasswordMessages();
        } else{

            return Messages.generateInvalidUsernameMessages();
        }
    }


    public static Result loginController() {

        JsonNode info;
        if (request().method().equalsIgnoreCase("post")) {
            Form<UserForm> loginForm = Form.form(UserForm.class).bindFromRequest();
            try {
                UserForm userForm = loginForm.get();
                return ok(loginUserToSystem(User.getUser(userForm), userForm).toJsonResponse());

            } catch (IllegalStateException e) {
                System.out.println(loginForm.errorsAsJson());
                return ok(loginForm.errorsAsJson());
            }
        }
        Content html = views.html.user.login.render();
        return ok(html);
    }

    public static Result signUpController() {

        if (request().method().equalsIgnoreCase("post")) {
            Form<UserSignUpForm> signUpForm = Form.form(UserSignUpForm.class).bindFromRequest();
            try {
                UserSignUpForm userSignUpForm = signUpForm.get();
                if (User.isUsernameTaken(userSignUpForm)) {
                    Messages msg = Messages.generateTakenUsernameMessage();
                    return ok(msg.toJsonResponse());
                } else {
                    Customer customer = new Customer(userSignUpForm);
                    CustomerDAOWrapper.getInstance().getCustomerDAO().save(customer);
                }
            } catch (IllegalStateException e) {
                return ok(signUpForm.errorsAsJson());
            }
        } else {
            Content html = views.html.user.signup.render();
            return ok(html);
        }
        return null;
    }


    public static Result doctorSignupController() {
        if(request().method().equalsIgnoreCase("post")){
            Form<DoctorSignUpForm> doctorSignUpFormForm = Form.form(DoctorSignUpForm.class).bindFromRequest();
            try {
                DoctorSignUpForm drSignUpForm = doctorSignUpFormForm.get();
                Doctor doctor = new Doctor(drSignUpForm);
                DoctorDAOWrapper.getInstance().getDoctorDAO().save(doctor);
                Messages msg = Messages.generateSuccessfulSignUpMessage();
                return ok(msg.toJsonResponse());
            }
            catch (IllegalStateException e){
                return ok(doctorSignUpFormForm.errorsAsJson());
            }
        }
        Content html = views.html.user.doctorSignup.render();
        return ok(html);
    }

    @Security.Authenticated(Secured.class)
    public static Result logout() {
        session().clear();
        SessionIdPool.removeUser(session().get("sessionId"));
        return redirect(routes.Application.index());
    }
}

