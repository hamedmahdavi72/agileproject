package forms;

import config.Messages;
import models.Doctor;
import models.User;

/**
 * Created by HamedMahdavi on 12/31/2016.
 */
public class DoctorEditValidator extends UserEditValidator{
    Doctor doctor;
    DoctorProfileForm doctorProfileForm;

    public DoctorEditValidator(Doctor doctor, DoctorProfileForm doctorProfileForm){
        this.doctor = doctor;
        this.doctorProfileForm = doctorProfileForm;
    }



    @Override
    public User getUser() {
        return doctor;
    }

    @Override
    public UserProfileForm getUserProfileForm() {
        return doctorProfileForm;
    }

    @Override
    protected void validateSpecificFields() {
        if(message == null){
            message = Messages.generateSuccessfulDoctorEditMessage();
        }
    }
}
