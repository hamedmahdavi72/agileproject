package forms;

import config.Areas;
import models.MongoLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ARYA on 12/23/2016.
 */
public class SearchForm {

    private String firstName;

    private String lastName;

    private String speciality;

    private String areaName;

    private String email;

    private String clinicAddress;

    private List<String> insuranceCompanies;
    public SearchForm(){

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if(firstName == null)
            this.firstName = "";
        else
            this.firstName = firstName;

    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if(lastName == null)
            this.lastName = "";
        else
            this.lastName = lastName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        if(speciality == null)
            this.speciality = "";
        else
            this.speciality = speciality;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        if(areaName == null)
            this.areaName = "";
        else
            this.areaName = areaName;
    }

    public double findLatitude(){
        return findArea().getLatitude();
    }

    public MongoLocation findArea() {
        return Areas.getInstance().getArea(areaName);
    }

    public double findLongitude(){
        return findArea().getLongitude();
    }

    public void eliminateNulls(){
        if(areaName == null)
            this.areaName = "";
        if(speciality == null)
            this.speciality = "";
        if(lastName == null)
            this.lastName = "";
        if(firstName == null)
            this.firstName = "";
        if(insuranceCompanies == null){
            insuranceCompanies = new ArrayList<>();
        }

    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getInsuranceCompanies() {
        return insuranceCompanies;
    }

    public void setInsuranceCompanies(List<String> supportedInsuranceCompanies) {
        if(supportedInsuranceCompanies.size() > 0)
            this.insuranceCompanies = supportedInsuranceCompanies;
        else this.insuranceCompanies = new ArrayList<>();
    }
}
