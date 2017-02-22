package forms;

import models.User;

/**
 * Created by HamedMahdavi on 12/31/2016.
 */
public class UserProfileForm extends UserBasicInfo {
    private String confirmPassword;
    private String password;

    public UserProfileForm(User user) {
        setLastName(user.getLastName());
        setFirstName(user.getFirstName());
        setMobileNumber(user.getMobileNumber());
        setNationalId(user.getNationalId());

    }

    public UserProfileForm(){}


    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
