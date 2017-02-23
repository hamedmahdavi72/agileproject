package forms;

import queryresult.AppointmentsData;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by HamedMahdavi on 2/15/2017.
 */
public class CustomerAppointmentsDataForm extends UserBasicInfo{
   private String customerUsername;
   private String doctorUsername;
   private ArrayList<Date> appointmentsDate;

   public CustomerAppointmentsDataForm(AppointmentsData data){
        setCustomerUsername(data.get_id().getCustomerUsername());
        setDoctorUsername(data.get_id().getDoctorUsername());
        setFirstName(data.getCustomer().getFirstName());
        setLastName(data.getCustomer().getLastName());
        setNationalId(data.getCustomer().getNationalId());
        setMobileNumber(data.getCustomer().getMobileNumber());
        setAppointmentsDate(data.getAppointmentsDate());

   }

   public CustomerAppointmentsDataForm(){}

    public void setDoctorUsername(String doctorUsername) {
        this.doctorUsername = doctorUsername;
    }

    public String getDoctorUsername() {
        return doctorUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setAppointmentsDate(ArrayList<Date> appointmentsDate) {
        this.appointmentsDate = appointmentsDate;
    }

    public ArrayList<Date> getAppointmentsDate() {
        return appointmentsDate;
    }
}
