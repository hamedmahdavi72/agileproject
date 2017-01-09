package controllers;

import dao.AppointmentDAOWrapper;
import dao.DoctorDAOWrapper;
import forms.DoctorInfoForm;
import models.Appointment;
import models.User;
import org.jongo.MongoCursor;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.twirl.api.Content;

import java.util.Date;

/**
 * Created by HamedMahdavi on 1/6/2017.
 */
public class DoctorsHandler extends Controller {
    public static Result doctorPageController(String username){
        if(User.isDoctor(username)){
            return ok(views.html.user.doctorPageAsUser.render(username));
        }
        else{
            return notFound();
        }
    }

    public static Result getDoctorInfo(String username) {
        DoctorInfoForm doctorInfo = new DoctorInfoForm(DoctorDAOWrapper.getInstance().findByUsername(username));
        return ok(Json.toJson(doctorInfo));
    }

    @Security.Authenticated(Secured.class)
    public static Result getDoctorAppointments(String username){
        if(User.isDoctor(username)){

            Date dateobj = new Date();
            MongoCursor<Appointment> appointments = AppointmentDAOWrapper.getInstance().
                    getAppointmentsAfterSpecificDate(username,dateobj);
            return ok(Json.toJson(appointments));
        }
        else
            return null;
    }


}
