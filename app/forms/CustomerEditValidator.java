package forms;

import config.Messages;
import models.Customer;
import models.User;

/**
 * Created by HamedMahdavi on 12/31/2016.
 */


public class CustomerEditValidator extends UserEditValidator {
    Customer customer;
    CustomerProfileForm customerProfileForm;

    public CustomerEditValidator(Customer customer, CustomerProfileForm customerProfileForm) {
        this.customer =customer;
        this.customerProfileForm = customerProfileForm;
    }


    @Override
    public User getUser() {
        return customer;
    }

    @Override
    public UserProfileForm getUserProfileForm() {
        return customerProfileForm;
    }

    @Override
    protected void validateSpecificFields() {
        if(message == null){
            message = Messages.generateSuccessfulCustomerEditMessage();
        }
    }
}
