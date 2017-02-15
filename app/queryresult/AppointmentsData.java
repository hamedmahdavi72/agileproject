package queryresult;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dao.CustomerDAOWrapper;
import forms.CustomerAppointmentsDataForm;
import models.Customer;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by HamedMahdavi on 2/15/2017.
 */
public class AppointmentsData {
    private AppointmentsDataId _id;
    private ArrayList<Date> appointmentsDate;
    @JsonIgnore
    private CustomerAppointmentsDataForm customerAppointmentsDataForm;

    public AppointmentsDataId get_id() {
        return _id;
    }

    public void set_id(AppointmentsDataId _id) {
        this._id = _id;
    }

    public ArrayList<Date> getAppointmentsDate() {
        return appointmentsDate;
    }

    public void setAppointmentsDate(ArrayList<Date> appointmentsDate) {
        this.appointmentsDate = appointmentsDate;
    }

    @JsonIgnore
    public Customer getCustomer(){
        return CustomerDAOWrapper.getInstance().findByUsername(_id.getCustomerUsername());
    }

    @JsonIgnore
    public CustomerAppointmentsDataForm getCustomerAppointmentsDataForm() {
        if(customerAppointmentsDataForm == null)
            return new CustomerAppointmentsDataForm(this);
        else
            return customerAppointmentsDataForm;
    }
}


