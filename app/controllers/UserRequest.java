package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import config.Messages;
import dao.AppointmentRequestDAOWrapper;
import dao.CustomerDAOWrapper;
import dao.DoctorDAOWrapper;
import forms.*;
import models.*;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.twirl.api.Content;

import javax.print.Doc;

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

    public static Messages loginAdminToSystem(Admin admin, UserForm userForm) {
        if (admin != null && admin.getPassword().equals(userForm.getPassword())) {
            session().clear();
            session("sessionId", SessionIdPool.addUser(admin.getUsername()));
            return Messages.generateSuccessfulAdminLoginMessage();
        } else if (admin != null && !admin.getPassword().equals(userForm.getPassword())) {

            return Messages.generateWrongPasswordMessages();
        } else {

            return Messages.generateInvalidUsernameMessages();
        }
    }


    public static Result loginController() {

        JsonNode info;
        if (request().method().equalsIgnoreCase("post")) {
            Form<UserForm> loginForm = Form.form(UserForm.class).bindFromRequest();
            try {
                UserForm userForm = loginForm.get();
                return ok(loginUserToSystem(User.getUser(userForm.getUsername()), userForm).toJsonResponse());

            } catch (IllegalStateException e) {
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
                if (User.isUsernameTaken(userSignUpForm.getUsername())) {
                    Messages msg = Messages.generateTakenUsernameMessage();
                    return ok(msg.toJsonResponse());
                } else {
                    Customer customer = new Customer(userSignUpForm);
                    CustomerDAOWrapper.getInstance().getCustomerDAO().save(customer);
                    session().clear();
                    session("sessionId", SessionIdPool.addUser(customer.getUsername()));
                    Messages msg = Messages.generateSuccessfulSignUpMessage();
                    return ok(msg.toJsonResponse());
                }
            } catch (IllegalStateException e) {
                return ok(signUpForm.errorsAsJson());
            }
        } else {
            Content html = views.html.user.signup.render();
            return ok(html);
        }
    }


    public static Result doctorSignupController() {
        if (request().method().equalsIgnoreCase("post")) {
            Form<DoctorSignUpForm> doctorSignUpFormForm = Form.form(DoctorSignUpForm.class).bindFromRequest();
            try {
                DoctorSignUpForm drSignUpForm = doctorSignUpFormForm.get();
                Doctor doctor = new Doctor(drSignUpForm);
                DoctorDAOWrapper.getInstance().getDoctorDAO().save(doctor);
                Messages msg = Messages.generateSuccessfulSignUpMessage();
                return ok(msg.toJsonResponse());
            } catch (IllegalStateException e) {
                return ok(doctorSignUpFormForm.errorsAsJson());
            }
        }
        Content html = views.html.user.doctorSignup.render();
        return ok(html);
    }

    @Security.Authenticated(Secured.class)
    public static Result loadProfile() {
        String username = SessionIdPool.getUsername(session().get("sessionId"));
        if (User.isCustomer(username)) {
            Content html = views.html.user.customerProfile.render();
            return ok(html);
        } else if (User.isDoctor(username)) {
            Content html = views.html.user.doctorPage.render();
            return ok(html);
        } else return ok(Json.toJson("Access Denied!"));
    }


    @Security.Authenticated(Secured.class)
    public static Result customerEditProfileController() {
        Content html = views.html.user.customerProfile.render();
        return ok(html);
    }

    @Security.Authenticated(Secured.class)
    public static Result getUser() {
        String username = getUsername();
        if (User.isCustomer(username)) {
            Customer customer = CustomerDAOWrapper.getInstance().findByUsername(username);
            return ok(Json.toJson(new CustomerProfileForm(customer)));
        } else if (User.isDoctor(username)) {
            Doctor doctor = DoctorDAOWrapper.getInstance().findByUsername(username);
            return ok(Json.toJson(new DoctorProfileForm(doctor)));
        } else return ok(Json.toJson("object is null"));
    }

    @Security.Authenticated(Secured.class)
    public static Result editCustomer() {
        String username = getUsername();
        if(User.isCustomer(username)) {
            Form<CustomerProfileForm> form = Form.form(CustomerProfileForm.class).bindFromRequest();
            Customer customer = CustomerDAOWrapper.getInstance().findByUsername(username);

            try {
                CustomerProfileForm customerProfileForm = form.get();
                CustomerEditValidator validator = new CustomerEditValidator(customer, customerProfileForm);
                validator.validate();

                if (validator.isSuccessful()) {
                    System.out.println("customer password " + customer.getPassword());
                    CustomerDAOWrapper.getInstance().getCustomerDAO().save(customer);
                }
                return ok(validator.getMessage().toJsonResponse());

            } catch (IllegalStateException e) {
                return ok(form.errorsAsJson());
            }
        }
        else if(User.isDoctor(username)){
            Form<DoctorProfileForm> form = Form.form(DoctorProfileForm.class).bindFromRequest();
            Doctor doctor = DoctorDAOWrapper.getInstance().findByUsername(username);
            try {
                DoctorProfileForm doctorProfileForm = form.get();
                DoctorEditValidator validator = new DoctorEditValidator(doctor, doctorProfileForm);
                validator.validate();
                if(validator.isSuccessful()){
                    DoctorDAOWrapper.getInstance().getDoctorDAO().save(doctor);
                }
                return ok(validator.getMessage().toJsonResponse());
            }catch (Exception e){
                return ok(form.errorsAsJson());
            }
        }
        return badRequest();
    }

    @Security.Authenticated(Secured.class)
    public static Result doctorProfile() {
        String username = getUsername();
        Doctor doctor = DoctorDAOWrapper.getInstance().findByUsername(username);
        if (doctor != null) {
            Content html = views.html.user.doctorPage.render();
            return ok(html);
        } else return redirect(routes.Application.index());
    }

    @Security.Authenticated(Secured.class)
    public static Result logout() {
        SessionIdPool.removeUser(session().get("sessionId"));
        session().clear();
        return redirect(routes.Application.index());
    }

    public static Result isLoggedIn() {
        return ok(Json.toJson(Secured.isLoggedIn(session())));
    }


    @Security.Authenticated(Secured.class)
    public static Result saveAppointmentRequest() {

        if (User.isCustomer(getUsername())) {
            Form<AppointmentRequestForm> form = Form.form(AppointmentRequestForm.class).bindFromRequest();
            AppointmentRequestForm appointmentRequestForm = form.get();
            AppointmentRequest appointmentRequest = new AppointmentRequest(appointmentRequestForm,getUsername());
            AppointmentRequestDAOWrapper.getInstance().getAppointmentRequestDAO().save(appointmentRequest);
            return ok("ok");
        }
        else{
            return badRequest();
        }


    }

    public static Result docCalendar(){
        Content html = views.html.user.doctor.dashboard.calendar.render();
        return ok(html);
    }

    public static Result docInfo(){
        Content html = views.html.user.doctor.dashboard.profile.render();
        return ok(html);
    }

    private static String getUsername() {
        return SessionIdPool.getUsername(session().get("sessionId"));
    }

}

