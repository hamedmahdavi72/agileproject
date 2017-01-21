package controllers;

import dao.AppointmentDAOWrapper;
import dao.AppointmentRequestDAOWrapper;
import dao.DoctorDAOWrapper;
import forms.AcceptAppointmentForm;
import forms.DoctorInfoForm;
import models.Appointment;
import models.AppointmentRequest;
import models.User;
import org.bson.types.ObjectId;
import org.jongo.MongoCursor;
import play.data.Form;
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

    @Security.Authenticated(Secured.class)
    public static Result doctorPageController(String username){
        if(User.isCustomer(getUsername()) && User.isDoctor(username)){
            return ok(views.html.user.doctorPageAsUser.render(username));
        }
        else{
            return notFound();
        }
    }

    @Security.Authenticated(Secured.class)
    public static Result getDoctorInfo(String username) {
        DoctorInfoForm doctorInfo = new DoctorInfoForm(DoctorDAOWrapper.getInstance().findByUsername(username));
        return ok(Json.toJson(doctorInfo));
    }

    @Security.Authenticated(Secured.class)
    public static Result getDoctorAppointments(){

        if(User.isDoctor(getUsername())){

            Date nowDate = new Date();
            MongoCursor<Appointment> acceptedAppointments = AppointmentDAOWrapper.getInstance().
                    getAppointmentsAfterSpecificDate(getUsername(),nowDate);
            return ok(Json.toJson(acceptedAppointments));
        }
        else
            return notFound();
    }

    @Security.Authenticated(Secured.class)
    public static Result getDoctorAppointmentRequests(){
        if(User.isDoctor(getUsername())){
            MongoCursor<AppointmentRequest> result = AppointmentRequestDAOWrapper.getInstance()
                    .findNotAnsweredRequests(getUsername());
            return ok(Json.toJson(result));
        }
        else
            return notFound();
    }

    @Security.Authenticated(Secured.class)
    public static  Result acceptedAppointmentRequest(){
        if(User.isDoctor(getUsername())){
                return ok(views.html.user.doctor.dashboard.acceptedAppointments.render());
        }
        else
            return notFound();
    }

    public static Result acceptAppointmentRequest(){
        if(User.isDoctor(getUsername())){
            try {
                AcceptAppointmentForm acceptAppointmentForm = Form.form(AcceptAppointmentForm.class)
                        .bindFromRequest().get();

                findAppointmentRequestAndSetAnsweredTrue(acceptAppointmentForm.getId());

                //saves new appointment from request
                Appointment appointment = new Appointment(getUsername(), acceptAppointmentForm);
                AppointmentDAOWrapper.getInstance().getAppointmentDAO().save(appointment);

                return ok();
            }
            catch (Exception e){
                return badRequest();
            }
        }
        else
            return notFound();
    }


    @Security.Authenticated(Secured.class)
    public static Result getAppointmentPanel(){
        Content html = views.html.user.doctor.dashboard.appointments.render();
        return  ok(html);
    }

    private static String getUsername() {
        return SessionIdPool.getUsername(session().get("sessionId"));
    }

    private static void findAppointmentRequestAndSetAnsweredTrue(ObjectId id) {
        AppointmentRequest appointmentRequest = AppointmentRequestDAOWrapper.getInstance()
                .findById(id);
        appointmentRequest.setAnswered(true);
        AppointmentRequestDAOWrapper.getInstance().getAppointmentRequestDAO().save(appointmentRequest);
    }


}
