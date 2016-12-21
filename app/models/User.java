package models;

/**
 * Created by HamedMahdavi on 12/11/2016.
 */


import com.fasterxml.jackson.annotation.JsonIgnore;
import dao.CustomerDAOWrapper;
import dao.DoctorDAOWrapper;
import forms.UserForm;
import forms.UserSignUpForm;
import org.bson.types.ObjectId;
import org.jongo.MongoCursor;
import org.jongo.marshall.jackson.oid.MongoId;

import java.util.Date;


public abstract class User extends JongoModel {


    protected String username;
    protected String password;
    protected String mobileNumber;
    protected String nationalId;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected Date registrationDate;




    public User(){

    }




    //getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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



    //utils

    public static boolean isDoctor(UserForm userForm) {
        return DoctorDAOWrapper.getInstance().findByUsername(userForm.getUsername()) != null;
    }

    public static boolean isCustomer(UserForm userForm) {
        return CustomerDAOWrapper.getInstance().findByUsername(userForm.getUsername()) != null;
    }

    public static boolean isUsernameTaken(UserSignUpForm userSignUpForm) {
        return isCustomer(userSignUpForm)|| isDoctor(userSignUpForm);
    }

    public static User getUser(UserForm userForm){
        User user = null;
        if(isCustomer(userForm)){
            user = CustomerDAOWrapper.getInstance().findByUsername(userForm.getUsername());
        }
        else if(isDoctor(userForm)){
            user = DoctorDAOWrapper.getInstance().findByUsername(userForm.getUsername());
        }

        return user;
    }
}