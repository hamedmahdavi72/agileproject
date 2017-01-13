package forms;

import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Created by HamedMahdavi on 1/13/2017.
 */
public class AcceptAppointmentForm {
    ObjectId id;
    String customerUsername;
    Date date;

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getId() {
        return id;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }
}


