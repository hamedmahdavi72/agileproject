package controllers;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Date;

/**
 * Created by ARYA on 1/8/2017.
 */
public class Utils extends Controller {
    public static Result convertDateToMillisecond(){
        String date = request().body().asJson().asText();
        Date current = new Date(date);
        long milliseconds = current.getTime();
        return ok(Json.toJson(milliseconds));
    }
}
