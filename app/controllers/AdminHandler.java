package controllers;

import dao.AdminDAOWrapper;
import dao.DoctorDAOWrapper;
import forms.UserForm;
import models.Doctor;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.data.Form;
import play.mvc.Result;
import play.twirl.api.Content;
import config.Messages;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hamed on 12/20/16 AD.
 */
public class AdminHandler extends Controller {

    public static Result adminLoginController(){
        if(request().method().equalsIgnoreCase("post")){
            Form<UserForm> form = Form.form(UserForm.class).bindFromRequest();
            try{
                UserForm userForm = form.get();
                //if(AdminDAOWrapper.getInstance().findByUsername(userForm.getUsername())!= null){
                    return ok(Messages.generateSuccessfulAdminLoginMessage().toJsonResponse());
                //}

            }catch (IllegalStateException e){
                return ok(form.errorsAsJson());
            }
        }
        Content html = views.html.admin.adminLogin.render();
        return ok(html);

    }

    public static Result adminPanelController(){
        Content html = views.html.admin.admin.render();
        return ok(html);
    }

    public static Result loadAdmin(){
        Map<String, Iterable<Doctor>> obj = new HashMap<>();
        obj.put("accepted", DoctorDAOWrapper.getInstance().findByAccepted(true));
        obj.put("pending", DoctorDAOWrapper.getInstance().findByAccepted(false));
        return ok(Json.toJson(obj));
    }


}
