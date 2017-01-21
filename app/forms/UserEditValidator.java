package forms;

import config.Messages;
import models.User;

/**
 * Created by HamedMahdavi on 12/31/2016.
 */
public abstract class UserEditValidator {


    public abstract User getUser();
    public abstract UserProfileForm getUserProfileForm();
    protected Messages message;
    private boolean successful = true;

    public void validateBasicInfo(){

        validateFirstName();
        validateLastName();
        validateMobileNumber();
        validateNationalId();
        validatePassword();

    }

    public void validate(){
        validateBasicInfo();
        validateSpecificFields();
    }

    protected abstract void validateSpecificFields();


    private void validatePassword() {
        if(getUserProfileForm().getPassword() != null &&
                getUserProfileForm().getConfirmPassword() != null &&
                getUserProfileForm().getPassword().equals(getUserProfileForm().getConfirmPassword())){
            if(getUserProfileForm().getPassword().length() >= 6){
                getUser().setPassword(getUserProfileForm().getPassword());
                System.out.println(getUser().getPassword());
            }
            else {
                this.successful = false;
                message = Messages.generateWrongPasswordMessages();
            }
        }
    }

    private void validateNationalId() {
        if(getUserProfileForm().getNationalId() != null &&
                !getUserProfileForm().getNationalId().equals(getUser().getNationalId())){
            getUser().setNationalId(getUserProfileForm().getNationalId());
        }
    }

    private void validateMobileNumber() {
        if(getUserProfileForm().getMobileNumber() != null &&
                !getUserProfileForm().getMobileNumber().equals(getUser().getMobileNumber())){
            getUser().setMobileNumber(getUserProfileForm().getMobileNumber());
        }
    }

    private void validateLastName() {
        if(getUserProfileForm().getLastName() != null &&
                !getUserProfileForm().getLastName().equalsIgnoreCase(getUser().getLastName())){
            getUser().setLastName(getUserProfileForm().getLastName());
        }
    }

    private void validateFirstName() {
        if(getUserProfileForm().getFirstName() != null &&
                !getUserProfileForm().getFirstName().equalsIgnoreCase(getUser().getFirstName())){
            getUser().setFirstName(getUserProfileForm().getFirstName());
        }
    }


    public Messages getMessage() {
        return message;
    }

    public void setMessage(Messages message) {
        this.message = message;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
