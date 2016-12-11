package models;

/**
 * Created by HamedMahdavi on 12/11/2016.
 */


import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.marshall.jackson.oid.MongoId;

import play.Play;
import uk.co.panaxiom.playjongo.PlayJongo;



public class User {

    public static PlayJongo jongo = Play.application().injector().instanceOf(PlayJongo.class);

    public static MongoCollection users() {
        return jongo.getCollection("users");
    }

    @MongoId
    public ObjectId id;

    public String name;

    public User(){
        id = new ObjectId();
        name = "Hamed";
    }
    public User insert() {
        users().save(this);
        return this;
    }

    public void remove() {
        users().remove(this.id);
    }

    public static User findByName(String name) {
        return users().findOne("{name: #}", name).as(User.class);
    }

}