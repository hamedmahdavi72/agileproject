package models;

import forms.DoctorSignUpForm;
import org.bson.types.ObjectId;

import java.nio.file.Path;
import java.util.Date;
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

    public Doctor(){

    }

    public Doctor(DoctorSignUpForm doctorSignUpForm){
        this.password = doctorSignUpForm.getPassword();
        this.clinicAddress = doctorSignUpForm.getClinicAddress();
        this.mobileNumber = doctorSignUpForm.getMobileNumber();
        this.firstName = doctorSignUpForm.getFirstName();
        this.lastName = doctorSignUpForm.getLastName();
        this.email = doctorSignUpForm.getEmail();
        this.accepted = false;
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
}
