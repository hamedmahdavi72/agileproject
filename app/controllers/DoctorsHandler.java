package controllers;

import dao.DoctorDAOWrapper;
import forms.DoctorInfoForm;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Content;

/**
 * Created by HamedMahdavi on 1/6/2017.
 */
public class DoctorsHandler extends Controller {
    public static Result doctorPageController(String username){
        if(User.isDoctor(username)){
            return ok(views.html.user.doctorPageAsUser.render());
        }
        else{
            return notFound();
        }
    }

    public static Result getDoctorInfo(String username) {
        DoctorInfoForm doctorInfo = new DoctorInfoForm(DoctorDAOWrapper.getInstance().findByUsername(username));
        return ok(Json.toJson(doctorInfo));
    }
}
