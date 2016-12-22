package controllers;

import dao.DoctorDAOWrapper;
import forms.SearchForm;
import models.Doctor;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Content;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ARYA on 12/23/2016.
 */
public class Search extends Controller {
    public static Result search(){


        if(request().method().equalsIgnoreCase("post")){
            Form<SearchForm> form = Form.form(SearchForm.class).bindFromRequest();
            try{
                    SearchForm searchForm = form.get();
                Iterable<Doctor> doctors = null;
                if(searchForm.getDoctorFirstName() != null && searchForm.getDoctorLastName() != null){
                        //TODO search by name
                    }
                    if(searchForm.getDoctorSpeciality() != null){
                        doctors = DoctorDAOWrapper.getInstance().findBySpeciality(searchForm.getDoctorSpeciality());
                    }
                    if(searchForm.getGeoLocation() != null){
                    }

                    Map<String, Iterable<Doctor>> obj = new HashMap<>();
                    obj.put("results", doctors);
                        if(doctors == null){
                            Map<String, String> res = new HashMap<>();
                            res.put("results", "no results!");
                            return ok(Json.toJson(res));
                        }
                        else
                            return ok(Json.toJson(obj));

            } catch (IllegalStateException e){
                return ok(form.errorsAsJson());
            }
        }

        Content html = views.html.search.search.render();
        return ok(html);

    }
}
