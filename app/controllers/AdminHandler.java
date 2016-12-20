package controllers;

import dao.AdminDAOWrapper;
import dao.DoctorDAOWrapper;
import forms.UserForm;
import models.Admin;
import models.Doctor;
import play.libs.Json;
import play.mvc.Controller;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import play.twirl.api.Content;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hamed on 12/20/16 AD.
 */
public class AdminHandler extends Controller {

    static UserRequest userRequest = new UserRequest();
    static Admin admin;
    public static Result adminLoginController(){
        if(request().method().equalsIgnoreCase("post")){
            Form<UserForm> form = Form.form(UserForm.class).bindFromRequest();
            try{
                UserForm userForm = form.get();
                admin = AdminDAOWrapper.getInstance().findByUsername(userForm.getUsername());
                return ok(userRequest.loginUserToSystem(admin, userForm).toJsonResponse());

            }catch (IllegalStateException e){
                return ok(form.errorsAsJson());
            }
        }
        if(SessionIdPool.getUsername(session().get("sessionId")) != null
                &&
                admin != null
                &&
           SessionIdPool.getUsername(session().get("sessionId")).equals(admin.getUsername()))

            return redirect(routes.AdminHandler.adminPanelController());
        Content html = views.html.admin.adminLogin.render();
        return ok(html);

    }

    @Security.Authenticated(Secured.class)
    public static Result adminPanelController(){
        if(admin != null &&
                SessionIdPool.getUsername(session().get("sessionId")).equals(admin.getUsername())){

            Content html = views.html.admin.admin.render();
            return ok(html);
        }
        else return redirect(routes.UserRequest.loginController());
    }

    @Security.Authenticated(Secured.class)
    public static Result loadAdmin(){
        Map<String, Iterable<Doctor>> obj = new HashMap<>();
        obj.put("accepted", DoctorDAOWrapper.getInstance().findByAccepted(true));
        obj.put("pending", DoctorDAOWrapper.getInstance().findByAccepted(false));
        return ok(Json.toJson(obj));
    }


}
