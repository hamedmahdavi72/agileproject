package models;

import forms.DoctorSignUpForm;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by HamedMahdavi on 12/13/2016.
 */
public class Doctor extends User {

    private String clinicAddress;
    private String clinicPhoneNumber;
    private DegreeInfo degree;
    private List<ObjectId> appointmentsList;

    public Doctor(){

    }

    public Doctor(DoctorSignUpForm doctorSignUpForm){
        this.password = doctorSignUpForm.getPassword();
        this.clinicAddress = doctorSignUpForm.getClinicAddress();
        this.mobileNumber = doctorSignUpForm.getMobileNumber();
        this.firstName = doctorSignUpForm.getFirstName();
        this.lastName = doctorSignUpForm.getLastName();
        this.email = doctorSignUpForm.getEmail();
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

    public DegreeInfo getDegree() {
        return degree;
    }

    public void setDegree(DegreeInfo degree) {
        this.degree = degree;
    }

    public List<ObjectId> getAppointmentsList() {
        return appointmentsList;
    }

    public void setAppointmentsList(List<ObjectId> appointmentsList) {
        this.appointmentsList = appointmentsList;
    }
}
