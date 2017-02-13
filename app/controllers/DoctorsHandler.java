package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dao.AppointmentDAOWrapper;
import dao.AppointmentRequestDAOWrapper;
import dao.CustomerDAOWrapper;
import dao.DoctorDAOWrapper;
import forms.AcceptAppointmentForm;
import forms.DoctorInfoForm;
import models.*;
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
import java.util.List;

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
    public static Result getDoctorFullInfo() {
        if(User.isDoctor(getUsername())){
            DoctorInfoForm doctorInfo = new DoctorInfoForm(DoctorDAOWrapper.getInstance().findByUsername(getUsername()));
            return ok(Json.toJson(doctorInfo));
        }
        else return badRequest();
    }

    @Security.Authenticated(Secured.class)
    public static Result getDoctorAppointments() throws ParseException {

        if(User.isDoctor(getUsername())){
            Date nowDate = new Date();
            MongoCursor<Appointment> acceptedAppointments = AppointmentDAOWrapper.getInstance().getAppointmentsAfterSpecificDate(getUsername(),nowDate);
            JsonNode results = Json.toJson(acceptedAppointments);
            return ok(results);
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

                acceptAppointmentForm.reviseId(request().body().asJson().get("id"));


                findAppointmentRequestAndSetAnsweredTrue(acceptAppointmentForm.getId());

                System.out.println(request().body().asJson());
//                System.out.println(new Date(request().body().asJson().get("date").toString()));

                //saves new appointment from request
                Appointment appointment = new Appointment(getUsername(), acceptAppointmentForm);
                AppointmentDAOWrapper.getInstance().getAppointmentDAO().save(appointment);

                return ok();
            }
            catch (Exception e){
                e.printStackTrace();
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

    @Security.Authenticated(Secured.class)
    public static Result saveDoctorInsurance() {
        if(User.isDoctor(getUsername())){
            Doctor doctor = DoctorDAOWrapper.getInstance().findByUsername(getUsername());
            ArrayList<String> insurances = new ArrayList<>();
            JsonNode req = request().body().asJson();
            for(int i = 0 ; i < req.size(); i++){
                insurances.add(req.get(i).toString());
            }
            doctor.setSupportedInsuranceCompanies(insurances);
            DoctorDAOWrapper.getInstance().getDoctorDAO().save(doctor);
            return ok();
        }
        else return badRequest();
    }

    @Security.Authenticated(Secured.class)
    public static Result getPatients() {
        if(User.isDoctor(getUsername())){
            Content html = views.html.user.doctor.dashboard.patients.render();
            return ok(html);
        }
        else return badRequest();
    }

    @Security.Authenticated(Secured.class)
    public static Result doctorPatients() {
        if(User.isDoctor(getUsername())){
            Date zeroDate = new Date(0L);
            MongoCursor<Appointment> acceptedAppointments = AppointmentDAOWrapper.getInstance().getAppointmentsAfterSpecificDate(getUsername(),zeroDate);
            MongoCursor<Customer> allCustomers = CustomerDAOWrapper.getInstance().findAll();

            for(Appointment appointment : acceptedAppointments){

            }
            JsonNode results = Json.toJson(acceptedAppointments);
            return ok(results);
        }
        else return badRequest();
    }
}
