package forms;

import play.data.validation.Constraints;

/**
 * Created by ARYA on 12/20/2016.
 */
public class DoctorSignUpForm{


    @Constraints.Required
    @Constraints.MinLength(6)
    private String password;

    @Constraints.Required
    private String firstName;

    @Constraints.Required
    private String lastName;

    private String mobileNumber;

    @Constraints.Required
    @Constraints.Email
    private String email;

    @Constraints.Required
    private String medicalOrgId;

    @Constraints.Required
    private String speciality;

    @Constraints.Required
    private String clinicAddress;


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

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMedicalOrgId(String medicalOrgId) {
        this.medicalOrgId = medicalOrgId;
    }

    public String getMedicalOrgId() {
        return medicalOrgId;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }
}
