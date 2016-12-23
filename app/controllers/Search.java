package controllers;

import dao.DoctorDAOWrapper;
import forms.SearchForm;
import models.Doctor;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ARYA on 12/23/2016.
 */
public class Search extends Controller {
    public static Result search() {


        if (request().method().equalsIgnoreCase("post")) {
            Form<SearchForm> form = Form.form(SearchForm.class).bindFromRequest();
            try {
                SearchForm searchForm = form.get();
                searchForm.eliminateNulls();
                Iterable<Doctor> doctors = DoctorDAOWrapper.getInstance().search(searchForm);
                List<SearchForm> results = new ArrayList<>();
                for(Doctor doctor : doctors){
                    SearchForm searchForm1 = new SearchForm();
                    searchForm1.setFirstName(doctor.getFirstName());
                    searchForm1.setLastName(doctor.getLastName());
                    searchForm1.setSpeciality(doctor.getSpeciality());
                    searchForm1.setEmail(doctor.getEmail());
                    results.add(searchForm1);
                }

                Map<String, List<SearchForm>> obj = new HashMap<>();
                obj.put("results", results);
                return ok(Json.toJson(obj));

            } catch (IllegalStateException e) {
                return ok(form.errorsAsJson());
            }
        }

        Content html = views.html.search.search.render();
        return ok(html);

    }
}
