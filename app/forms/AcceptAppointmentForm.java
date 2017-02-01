package forms;

import com.fasterxml.jackson.databind.JsonNode;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Created by HamedMahdavi on 1/13/2017.
 */
public class AcceptAppointmentForm {
    private ObjectId id;
    private String customerUsername;
    private Date date;

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void reviseId(JsonNode id){
        this.id = new ObjectId(id.get("timestamp").asInt(), id.get("machineIdentifier").asInt(),
                id.get("processIdentifier").shortValue(),id.get("counter").asInt());
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


