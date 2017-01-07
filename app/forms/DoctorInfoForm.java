package forms;

import models.Doctor;

import java.util.ArrayList;

/**
 * Created by HamedMahdavi on 1/6/2017.
 */
public class DoctorInfoForm {
    private String firstName;
    private String lastName;
    private String speciality;
    private String clinicAddress;
    private String clinicPhoneNumber;
    private String clinicInfo;
    private ArrayList<String> supportedInsuranceCompanies;


    public DoctorInfoForm(Doctor doctor){

        if(doctor != null) {
            this.firstName = doctor.getFirstName();
            this.lastName = doctor.getLastName();
            this.speciality = doctor.getSpeciality();
            this.clinicAddress = doctor.getClinicAddress();
            this.clinicPhoneNumber = doctor.getClinicPhoneNumber();
            this.clinicInfo = doctor.getClinicInfo();
            this.supportedInsuranceCompanies = doctor.getSupportedInsuranceCompanies();
        }
    }

    public DoctorInfoForm(){

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
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

    public String getClinicInfo() {
        return clinicInfo;
    }

    public void setClinicInfo(String clinicInfo) {
        this.clinicInfo = clinicInfo;
    }

    public ArrayList<String> getSupportedInsuranceCompanies() {
        return supportedInsuranceCompanies;
    }

    public void setSupportedInsuranceCompanies(ArrayList<String> supportedInsuranceCompanies) {
        this.supportedInsuranceCompanies = supportedInsuranceCompanies;
    }
}
