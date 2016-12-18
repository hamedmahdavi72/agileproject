package forms;
import play.data.validation.Constraints;
/**
 * Created by ARYA on 12/18/2016.
 */
public class UserSignUpForm extends UserForm {

        private String mobileNumber;

        @Constraints.Required
        private String nationalId;

        @Constraints.Required
        private String firstName;

        @Constraints.Required
        private String lastName;

        @Constraints.Required
        @Constraints.Email
        private String email;


    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
