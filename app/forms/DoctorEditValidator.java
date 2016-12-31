package forms;

import models.User;

/**
 * Created by HamedMahdavi on 12/31/2016.
 */
public class DoctorEditValidator extends UserEditValidator{

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public UserProfileForm getUserProfileForm() {
        return null;
    }

    @Override
    protected void validateSpecificFields() {

    }
}
