package models;

/**
 * Created by HamedMahdavi on 12/11/2016.
 */


import org.bson.types.ObjectId;
import org.jongo.MongoCursor;
import org.jongo.marshall.jackson.oid.MongoId;

import java.util.Date;


public class User extends JongoModel {

    @MongoId
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Date registrationDate;
    private static final String collectionName = User.class.getSimpleName();

    public User(){

    }




    //queries
    public  static MongoCursor<User> findByFirstName(String firstName) {

        return jongo.getCollection(collectionName).find("{firstName: #}", firstName).as(User.class);
    }
    public  static MongoCursor<User> findByLastName(String lastName) {

        return jongo.getCollection(collectionName).find("{lastName: #}", lastName).as(User.class);
    }
    public  static User findByUsername(String username) {

        return jongo.getCollection(collectionName).findOne("{username: #}", username).as(User.class);
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

    public static String getCollectionName() {
        return collectionName;
    }


}