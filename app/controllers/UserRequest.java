package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dao.CustomerDAOWrapper;
import dao.DoctorDAOWrapper;
import forms.UserForm;
import forms.UserSignUpForm;
import models.Customer;
import models.User;
import org.h2.engine.Session;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    Map<String , String > msg = new HashMap<>();
                    msg.put("loginmsg", "redirect");
                    return ok(Json.toJson(msg));
                }
                else {
                    Map<String, List<String>> errorMap = new HashMap<>();
                    List<String> messages = new ArrayList<>();
                    messages.add("Username Invalid");
                    errorMap.put("username", messages);
                    return ok(Json.toJson(errorMap));
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
                    Map<String, List<String>> errorMap = new HashMap<>();
                    List<String> messages = new ArrayList<>();
                    messages.add("Username Already Taken!");
                    errorMap.put("username", messages);
                    return ok(Json.toJson(errorMap));
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
}
