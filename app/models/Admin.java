package models;

import org.jongo.marshall.jackson.oid.MongoId;

/**
 * Created by Ala on 12/20/16 AD.
 */
public class Admin extends JongoModel {
    @MongoId
    private String username;
    private String password;

    public Admin(){}

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
}
