package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import dao.AdminDAOWrapper;
import dao.DoctorDAOWrapper;
import dao.IssueDAOWrapper;
import forms.IssueForm;
import forms.SolveForm;
import forms.UserForm;
import models.Admin;
import models.Doctor;
import models.Issue;
import org.bson.types.ObjectId;
import org.hamcrest.core.Is;
import org.jongo.MongoCursor;
import play.libs.Json;
import play.mvc.Controller;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import play.twirl.api.Content;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
                return ok(userRequest.loginAdminToSystem(admin, userForm).toJsonResponse());

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
        if(admin != null &&
                SessionIdPool.getUsername(session().get("sessionId")).equals(admin.getUsername())) {
            Map<String, Iterable<Doctor>> obj = new HashMap<>();
            obj.put("current", DoctorDAOWrapper.getInstance().findByAccepted(true));
            obj.put("pending", DoctorDAOWrapper.getInstance().findByAccepted(false));
            return ok(Json.toJson(obj));
        }
        else return redirect(routes.UserRequest.loginController());
    }

    @Security.Authenticated(Secured.class)
    public static Result rejectDoctor(){
        if(admin != null &&
                SessionIdPool.getUsername(session().get("sessionId")).equals(admin.getUsername())) {
            JsonNode doctorUsername = request().body().asJson();

            Doctor rejectedDoctor = DoctorDAOWrapper.getInstance().findByUsername(doctorUsername.textValue());
            rejectedDoctor.setAccepted(false);
            DoctorDAOWrapper.getInstance().getDoctorDAO().save(rejectedDoctor);

            return ok("removed");
        }
        else return redirect(routes.UserRequest.loginController());
    }

    @Security.Authenticated(Secured.class)
    public static Result acceptDoctor(){
        if(admin != null &&
                SessionIdPool.getUsername(session().get("sessionId")).equals(admin.getUsername())) {
            JsonNode doctorUsername = request().body().asJson();

            Doctor acceptedDoctor = DoctorDAOWrapper.getInstance().findByUsername(doctorUsername.textValue());
            acceptedDoctor.setAccepted(true);
            DoctorDAOWrapper.getInstance().getDoctorDAO().save(acceptedDoctor);

            return ok("accepted");
        }
        else return redirect(routes.UserRequest.loginController());
    }

    @Security.Authenticated(Secured.class)
    public static Result deleteDoctor(){
        if(admin != null &&
                SessionIdPool.getUsername(session().get("sessionId")).equals(admin.getUsername())) {
            JsonNode doctorUsername = request().body().asJson();

            Doctor deletedDoctor = DoctorDAOWrapper.getInstance().findByUsername(doctorUsername.textValue());
            DoctorDAOWrapper.getInstance().getDoctorDAO().remove(deletedDoctor);

            return ok("deleted");
        }
        else return redirect(routes.UserRequest.loginController());
    }


    @Security.Authenticated(Secured.class)
    public static Result getIssues(){
        if(admin != null &&
                SessionIdPool.getUsername(session().get("sessionId")).equals(admin.getUsername())) {
            MongoCursor<Issue> issues = IssueDAOWrapper.getInstance().findAll();
            return ok(Json.toJson(issues));
        }
        else return redirect(routes.UserRequest.loginController());
    }

    @Security.Authenticated(Secured.class)
    public static Result getIssuesId(){
        if(admin != null &&
                SessionIdPool.getUsername(session().get("sessionId")).equals(admin.getUsername())) {
            MongoCursor<Issue> issues = IssueDAOWrapper.getInstance().findAll();
            List<String> ids = new ArrayList<>();
            for(Issue issue : issues){
               ids.add(issue.getId().toString());
            }
            return ok(Json.toJson(ids));
        }
        else return redirect(routes.UserRequest.loginController());
    }

    @Security.Authenticated(Secured.class)
    public static Result solveIssue(){
        if(admin != null &&
                SessionIdPool.getUsername(session().get("sessionId")).equals(admin.getUsername())) {
                Form<SolveForm> solveFormForm = Form.form(SolveForm.class).bindFromRequest();
                SolveForm newSolveForm = solveFormForm.get();
                //TODO save
                Issue issue = IssueDAOWrapper.getInstance().findById(new ObjectId(newSolveForm.getObjectId()));
                IssueDAOWrapper.getInstance().getIssueDAO().remove(issue);
            return ok();
        }
        else return redirect(routes.UserRequest.loginController());
    }

}
