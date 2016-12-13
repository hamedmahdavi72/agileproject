package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.marshall.jackson.oid.MongoId;
import play.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

/**
 * Created by HamedMahdavi on 12/12/2016.
 * we suppose that every class collection name is it class simple name
 */
public abstract class JongoModel {
    protected static PlayJongo jongo = Play.application().injector().instanceOf(PlayJongo.class);


    @MongoId
    protected ObjectId id;

    public  MongoCollection getCollection() {
        return jongo.getCollection(this.getClass().getSimpleName());
    }

    public void save() {
        getCollection().save(this);
    }

    public void remove() {
        getCollection().remove(this.id);
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getId() {
        return id;
    }
}
