package models;

import forms.DoctorSignUpForm;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HamedMahdavi on 12/13/2016.
 */
public class Doctor extends User {

    private String clinicAddress;
    private String clinicPhoneNumber;
    private List<ObjectId> appointmentsList;
    private boolean accepted = false;
    private String speciality;
    private ArrayList<String> supportedInsuranceCompanies;
    private MongoLocation geoLocation;
    private String medicalOrgId;
    private String clinicInfo;

    public Doctor(){

    }

    public Doctor(DoctorSignUpForm doctorSignUpForm){
        supportedInsuranceCompanies = new ArrayList<>();
        this.password = doctorSignUpForm.getPassword();
        this.clinicAddress = doctorSignUpForm.getClinicAddress();
        this.mobileNumber = doctorSignUpForm.getMobileNumber();
        this.firstName = doctorSignUpForm.getFirstName();
        this.lastName = doctorSignUpForm.getLastName();
        this.speciality = doctorSignUpForm.getSpeciality();
        this.medicalOrgId = doctorSignUpForm.getMedicalOrgId();
        this.email = doctorSignUpForm.getEmail();
        this.setUsername(this.email);
        this.accepted = false;
        geoLocation = new MongoLocation();
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public String getClinicPhoneNumber() {
        return clinicPhoneNumber;
    }

    public void setClinicPhoneNumber(String clinicPhoneNumber) {
        this.clinicPhoneNumber = clinicPhoneNumber;
    }



    public List<ObjectId> getAppointmentsList() {
        return appointmentsList;
    }

    public void setAppointmentsList(List<ObjectId> appointmentsList) {
        this.appointmentsList = appointmentsList;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean getAccepted(){
        return accepted;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public ArrayList<String> getSupportedInsuranceCompanies() {
        return supportedInsuranceCompanies;
    }

    public void setSupportedInsuranceCompanies(ArrayList<String> supportedInsuranceCompanies) {
        this.supportedInsuranceCompanies = supportedInsuranceCompanies;
    }

    public void setGeoLocation(MongoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public MongoLocation getGeoLocation() {
        return geoLocation;
    }


    public void setMedicalOrgId(String medicalOrgId) {
        this.medicalOrgId = medicalOrgId;
    }

    public String getMedicalOrgId() {
        return medicalOrgId;
    }


    public void setClinicInfo(String clinicInfo) {
        this.clinicInfo = clinicInfo;
    }

    public String getClinicInfo() {
        return clinicInfo;
    }
}
