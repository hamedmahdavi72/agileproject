package controllers;

import dao.DoctorDAOWrapper;
import dao.IssueDAOWrapper;
import forms.SearchForm;
import models.Doctor;
import models.Issue;
import models.User;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Content;

import javax.print.Doc;
import java.util.*;

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
                    results.add(createSearchForm(searchForm1,doctor));
                }
                Map<String, List<SearchForm>> obj = new HashMap<>();
                obj.put("results", results);
                return ok(Json.toJson(obj));
            }
            catch (IllegalStateException e) {
                return ok(form.errorsAsJson());
            }
        }

        Content html = views.html.search.search.render();
        return ok(html);

    }

    public static Result searchAdvertise(SearchForm searchForm, Iterable<Doctor> doctors){
            searchForm.eliminateNulls();
            Iterable<Doctor> allAdvertisedDoctors = DoctorDAOWrapper.getInstance().findByAdvertise(true);
            List<Doctor> advertisedDoctors = intersection(((List<Doctor>) doctors), ((List<Doctor>) allAdvertisedDoctors));
            List<Doctor> selectedDoctorsForTopShow=getToptoShow(advertisedDoctors);//this is the doctors to put in top of the result page
            List<SearchForm> advertiseResults = new ArrayList<>();
            for(Doctor doctor : selectedDoctorsForTopShow){
                SearchForm searchForm1 = new SearchForm();
                advertiseResults.add(createSearchForm(searchForm1,doctor));
            }
            Map<String, List<SearchForm>> obj = new HashMap<>();
            obj.put("advertiseResults", advertiseResults);
            return ok(Json.toJson(obj));
    }

    public static SearchForm createSearchForm(SearchForm searchForm, Doctor doctor){
        searchForm.setFirstName(doctor.getFirstName());
        searchForm.setLastName(doctor.getLastName());
        searchForm.setSpeciality(doctor.getSpeciality());
        searchForm.setEmail(doctor.getEmail());
        searchForm.setClinicAddress(doctor.getClinicAddress());
        return searchForm;
    }

    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }

    public static List<Doctor> getToptoShow(List<Doctor> list){
        List<Doctor> samples = new ArrayList<Doctor>();
        Collections.shuffle(list);
        int counter =0;
        while (counter<3 && counter<list.size()){
            Doctor doc =list.get(counter);
            doc.countDown();
            if(doc.getTopShowedNum()==0){
                doc.setAdvertised(false);
                Issue issue = new Issue();
                issue.setIssueDate(new Date().toString());
                issue.setCustormerUsername(doc.getUsername());
                issue.setSolved(false);
                issue.setSubject("Advertisement Finished");
                issue.setIssueReport("Advertisement Reached Maximum click threshold");
                IssueDAOWrapper.getInstance().getIssueDAO().save(issue);
            }
            samples.add(doc);
            counter++;
            DoctorDAOWrapper.getInstance().getDoctorDAO().save(doc);
        }
        return samples;
    }

}
