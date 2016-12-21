package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import config.Messages;
import dao.CustomerDAOWrapper;
import dao.DoctorDAOWrapper;
import forms.CustomerProfileForm;
import forms.DoctorSignUpForm;
import forms.UserForm;
import forms.UserSignUpForm;
import models.Admin;
import models.Customer;
import models.Doctor;
import models.User;
import play.data.Form;
import play.libs.Json;
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
    public static Result customerEditProfileController(){
        Content html = views.html.user.customerProfile.render();
        return ok(html);
    }

    @Security.Authenticated(Secured.class)
    public static Result getCustomer(){
        String username = SessionIdPool.getUsername(session().get("sessionId"));
        Customer customer = CustomerDAOWrapper.getInstance().findByUsername(username);
        if(customer != null){
            CustomerProfileForm customerProfileForm = new CustomerProfileForm();
            customerProfileForm.setFirstName(customer.getFirstName());
            customerProfileForm.setLastName(customer.getLastName());
            customerProfileForm.setNationalId(customer.getNationalId());
            customerProfileForm.setMobileNumber(customer.getMobileNumber());
            return ok(Json.toJson(customerProfileForm));
        }
        else return ok(Json.toJson("object is null"));
    }

    @Security.Authenticated(Secured.class)
    public static Result editCustomer(){
        Form<CustomerProfileForm> form = Form.form(CustomerProfileForm.class).bindFromRequest();
        String username = SessionIdPool.getUsername(session().get("sessionId"));
        Customer customer = CustomerDAOWrapper.getInstance().findByUsername(username);

        try{
            CustomerProfileForm customerProfileForm = form.get();
            if(customerProfileForm.getFirstName() != null &&
                    !customerProfileForm.getFirstName().equalsIgnoreCase(customer.getFirstName())){
                customer.setFirstName(customerProfileForm.getFirstName());
            }
            if(customerProfileForm.getLastName() != null &&
                    !customerProfileForm.getLastName().equalsIgnoreCase(customer.getLastName())){
                customer.setLastName(customerProfileForm.getLastName());
            }
            if(customerProfileForm.getMobileNumber() != null &&
                    !customerProfileForm.getMobileNumber().equals(customer.getMobileNumber())){
                customer.setMobileNumber(customerProfileForm.getMobileNumber());
            }
            if(customerProfileForm.getNationalId() != null &&
                    !customerProfileForm.getNationalId().equals(customer.getNationalId())){
                customer.setNationalId(customerProfileForm.getNationalId());
            }
            if(customerProfileForm.getPassword() != null &&
                    customerProfileForm.getConfirmPassword() != null &&
                    customerProfileForm.getPassword().equals(customerProfileForm.getPassword()) &&
                    !customerProfileForm.getPassword().equals(customer.getPassword())){
                customer.setPassword(customerProfileForm.getPassword());
            }
            CustomerDAOWrapper.getInstance().getCustomerDAO().save(customer);

            Messages msg = Messages.generateSuccessfulCustomerEditMessage();
            return ok(Json.toJson(msg.toJsonResponse()));

        } catch (IllegalStateException e){
            System.out.println("exception");
            return ok(form.errorsAsJson());
        }
    }

    @Security.Authenticated(Secured.class)
    public static Result logout() {
        SessionIdPool.removeUser(session().get("sessionId"));
        session().clear();
        return redirect(routes.Application.index());
    }
}

