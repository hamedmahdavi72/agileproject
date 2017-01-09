package controllers;

import models.AppointmentInterval;
import org.joda.time.Interval;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by hamed on 1/8/17 AD.
 */
public class AppointmentHandler extends Controller {
//    private static int apppoinmentIntervalNum = 1000;

    public static Result getAppointment(){
        if(request().method().equalsIgnoreCase("post")){
        }
        return ok();
    }
}
