package models;


import forms.UserSignUpForm;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by HamedMahdavi on 12/13/2016.
 */
public class Customer extends User {
    private List<ObjectId> appointmentsList;

    public Customer(){}

    public Customer(UserSignUpForm userSignUpForm){

        setUsername(userSignUpForm.getUsername());
        setPassword(userSignUpForm.getPassword());
        setEmail(userSignUpForm.getEmail());
        setFirstName(userSignUpForm.getFirstName());
        setLastName(userSignUpForm.getLastName());
        setMobileNumber(userSignUpForm.getMobileNumber());
        setNationalId(userSignUpForm.getNationalId());

    }

}
