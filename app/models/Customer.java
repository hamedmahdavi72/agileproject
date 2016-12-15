package models;


import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by HamedMahdavi on 12/13/2016.
 */
public class Customer extends User {
    private List<ObjectId> appointmentsList;

}
