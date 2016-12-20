package config;

/**
 * Created by ARYA on 12/19/2016.
 */
public class MessageConstants {

    //Messages
    private final String InvalidUsernameMessage = "Username is Invalid!";
    private final String UsernameAlreadyTakenMessage = "Username is Already Taken!";
    private final String WrongPasswordMessage = "Wrong Password!";
    private final String RedirectMessage = "redirect";

    //Fields
    private final String RedirectLoginPageField = "loginmsg";
    private final String RedirectDoctorSignUpPageField = "signupmsg";
    private final String UsernameField = "username";
    private final String PasswordField = "password";
    private final String EmailField = "email";
    private final String NationalIdField = "nationalId";
    private final String FirstName = "firstName";
    private final String LastName = "lastName";

    private static MessageConstants instance = null;

    public static MessageConstants getInstance(){
        if(instance == null)
            instance = new MessageConstants();

        return instance;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmailField() {
        return EmailField;
    }

    public String getNationalIdField() {
        return NationalIdField;
    }

    public String getPasswordField() {
        return PasswordField;
    }

    public String getUsernameField() {
        return UsernameField;
    }

    public String getRedirectLoginPageField() {
        return RedirectLoginPageField;
    }

    public String getRedirectMessage() {
        return RedirectMessage;
    }

    public String getInvalidUsernameMessage() {
        return InvalidUsernameMessage;
    }

    public String getUsernameAlreadyTakenMessage() {
        return UsernameAlreadyTakenMessage;
    }

    public String getWrongPasswordMessage() {
        return WrongPasswordMessage;
    }

    public String getRedirectDoctorSignUpPageField() {
        return RedirectDoctorSignUpPageField;
    }
}
