package controllers;

/**
 * Created by HamedMahdavi on 2/13/2017.
 */

import com.fasterxml.jackson.databind.JsonNode;
import dao.AppointmentDAOWrapper;
import dao.AppointmentRequestDAOWrapper;
import dao.DoctorDAOWrapper;
import forms.AcceptAppointmentForm;
import forms.DoctorInfoForm;
import models.Appointment;
import models.AppointmentRequest;
import models.Doctor;
import models.User;
import org.bson.types.ObjectId;
import org.jongo.MongoCursor;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.twirl.api.Content;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class CustomerDashboardHandler extends Controller {
    public static Result getUserAppoinments(){
        if(User.isCustomer(getUsername())) {
            MongoCursor<Appointment> userAppointments = AppointmentDAOWrapper.getInstance()
                    .findByCustomerUsername(getUsername());
            return ok(Json.toJson(userAppointments));
        }
        else
            return badRequest();
    }

    private static String getUsername() {
        return SessionIdPool.getUsername(session().get("sessionId"));
    }

    @Security.Authenticated(Secured.class)
    public static Result customerInfo() {
        return ok(views.html.user.customer.dashboard.profile.render());
    }

    @Security.Authenticated(Secured.class)
    public static Result customerAppointmentsTemplate(){
        if(User.isCustomer(getUsername())){
            Content html = views.html.user.customer.dashboard.appointments.render();
            return ok(html);
        }
        else return badRequest();
    }
}
