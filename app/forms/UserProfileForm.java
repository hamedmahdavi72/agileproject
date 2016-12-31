package forms;

import models.User;

/**
 * Created by HamedMahdavi on 12/31/2016.
 */
public class UserProfileForm {
    protected String firstName;
    protected String lastName;
    protected String nationalId;
    protected String mobileNumber;
    private String confirmPassword;
    private String password;

    public UserProfileForm(User user) {
        lastName = user.getLastName();
        firstName = user.getFirstName();
        mobileNumber = user.getMobileNumber();
        nationalId = user.getNationalId();

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

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

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
